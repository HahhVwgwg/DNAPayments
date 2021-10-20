package com.dnapayments.presentation.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.dnapayments.R
import com.dnapayments.data.model.OtpResponse
import com.dnapayments.databinding.FragmentChooseParkBinding
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseParkFragment :
    BaseBindingFragment<FragmentChooseParkBinding, AuthorizationViewModel>(R.layout.fragment_choose_park) {
    override val vm: AuthorizationViewModel by sharedViewModel()
    private val prefsAuth: PrefsAuth by inject()
    private var otp: OtpResponse? = null
    private var phone: String? = null
    private var adapter: ChooseParkAdapter? = null

    override fun initViews(savedInstanceState: Bundle?) {
        phone = arguments?.getString("phone")
        otp = arguments?.getParcelable("otp")
        binding?.run {
            registerByToken()
            adapter = ChooseParkAdapter()
            parkElements.adapter = adapter
            vm.parkList.observe(viewLifecycleOwner, {
                adapter?.setData(it)
            })

            vm.tokenOtp.observe(viewLifecycleOwner, { tokenOtp ->
                tokenOtp.accessToken?.let {
                    prefsAuth.saveAccessToken(it)
                    prefsAuth.setAuthorized(true)
                    redirectToPinCodeActivity()
                }
            })

            generateBtn.setOnClickListener {
                adapter?.let {
                    vm.loginByOtp(prefsAuth.getAuthToken()!!,
                        "${(0..20000).random()}",
                        otp?.otp!!,
                        phone!!,
                        it.getSelected().toInt()
                    )
                }

            }
        }
    }

    private fun redirectToPinCodeActivity() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finishAffinity()
    }


    private fun registerByToken() {
        if (prefsAuth.getAuthToken() == null) {
            getToken()
            return
        }
        vm.getParks(prefsAuth.getAuthToken()!!, "${(0..20000).random()}", otp?.otp!!, phone!!)
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

}