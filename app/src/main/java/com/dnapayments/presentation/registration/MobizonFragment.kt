package com.dnapayments.presentation.registration

import android.app.Activity.RESULT_OK
import android.content.*
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import com.dnapayments.R
import com.dnapayments.data.model.OtpResponse
import com.dnapayments.databinding.FragmentMobizonBinding
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.SmsBroadcastReceiver
import com.dnapayments.utils.base.BaseBindingFragment
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*
import java.util.regex.Pattern


class MobizonFragment :
    BaseBindingFragment<FragmentMobizonBinding, AuthorizationViewModel>(R.layout.fragment_mobizon) {
    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    override val vm: AuthorizationViewModel by sharedViewModel()
    private val checkPoint: Int = 200
    private val prefsAuth: PrefsAuth by inject()
    private var phone: String? = null
    private var timer: CountDownTimer? = null
    private var otp: OtpResponse? = null

    override fun initViews(savedInstanceState: Bundle?) {
        phone = arguments?.getString("phone")
        otp = arguments?.getParcelable("otp")
        resetTimer()
        binding?.run {
            viewModel = vm
            sendCodeAgain.setOnClickListener {
                if (sendCodeAgain.text == requireContext().getString(R.string.send_again)) {
                    phone?.let {
                        vm.getOtpByPhoneNumber(it)
                    }
                    resetTimer()
                }
            }
            verifyBtn.setOnClickListener {
                otp?.let {
                    if (otpTextView.text.toString() == it.otp) {
                        registerByToken()
                    } else {
                        showMsg(getStr(R.string.entered_wrong_code_check_again))
                    }
                } ?: showMsg(getStr(R.string.entered_wrong_code_check_again))
            }
            vm.otpResponse.observe(viewLifecycleOwner, {
                otp = it
            })
            vm.tokenOtp.observe(viewLifecycleOwner, { tokenOtp ->
                tokenOtp.accessToken?.let {
                    prefsAuth.saveAccessToken(it)
                    prefsAuth.setAuthorized(true)
                    redirectToPinCodeActivity()
                }
            })
            vm.error.observe(viewLifecycleOwner, {
                redirectToChooseParkActivity()
            })
        }
        startSmartUserConsent()
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(smsBroadcastReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
    }

    private fun startSmartUserConsent() {
        val client = SmsRetriever.getClient(requireContext())
        client.startSmsUserConsent(null)
    }

    private fun registerByToken() {
        if (prefsAuth.getAuthToken() == null) {
            getToken()
            return
        }
        vm.loginByOtp(prefsAuth.getAuthToken()!!, "${(0..20000).random()}", otp?.otp!!, phone!!)
    }

    private fun redirectToPinCodeActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
    }

    private fun redirectToChooseParkActivity() {
        showMsg("redirectToChooseParkActivity")
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("device_token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            task.result?.let {
                prefsAuth.saveAuthToken(it)
                registerByToken()
            } ?: showMsg(getStr(R.string.something_went_wrong))
        })
    }

    private fun resetTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                vm.spendTime.set(SpendTime(getString(R.string.send_again_after_n_sec,
                    millisUntilFinished / 1000), false))
            }

            override fun onFinish() {
                vm.spendTime.set(SpendTime(getStr(R.string.send_again), true))
            }
        }.start()
    }

    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver!!.smsBroadcastReceiverListener =
            object : SmsBroadcastReceiver.SmsBroadcastReceiverListener {
                override fun onSuccess(intent: Intent?) {
                    startActivityForResult(
                        intent, checkPoint
                    )
                }

                override fun onFailure() {}
            }
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        activity?.registerReceiver(smsBroadcastReceiver, intentFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == checkPoint) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                getOtpFromMessage(message!!)
            }
        }
    }

    private fun getOtpFromMessage(message: String) {
        val otpPattern = Pattern.compile("(|^)\\d{6}")
        val matcher = otpPattern.matcher(message)
        binding?.run {
            if (matcher.find()) {
                otpTextView.setText(matcher.group(0))
            }
            Handler(Looper.getMainLooper()).postDelayed({
                otpProgressBar.visibility = View.VISIBLE
                if (otpTextView.text.toString() == otp?.otp) {
                    registerByToken()
                } else {
                    showMsg(getString(R.string.entered_wrong_code_check_again))
                }
            }, 200)
        }

    }

}