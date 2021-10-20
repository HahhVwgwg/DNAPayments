package com.dnapayments.presentation.pin_code

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.biometric.BiometricPrompt
import com.dnapayments.R
import com.dnapayments.databinding.BottomFragmentPinCodeBinding
import com.dnapayments.presentation.activity.RegistrationActivity
import com.dnapayments.utils.BiometricAuthListener
import com.dnapayments.utils.BiometricUtil
import com.dnapayments.utils.Earthquake
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingBottomFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

//@requestCode ->  10 - Set access code, 11 - Change access code
class PinCodeFragment(
    private val requestCode: Int,
    private val myCallBack: (result: Boolean) -> Unit,
) :
    BaseBindingBottomFragment<BottomFragmentPinCodeBinding>(R.layout.bottom_fragment_pin_code),
    BiometricAuthListener {

    private val viewModel: PinViewModel by viewModel()
    private var isCancelTouch = true
    private val prefsAuth: PrefsAuth by inject()

    override fun initViews(savedInstanceState: Bundle?) {
        activity?.let { activity ->
            isCancelable = requestCode != 1 && requestCode != 10
            binding?.let { b ->
                //  Bind viewModel
                b.viewModel = viewModel
                b.forgotPassword.setOnClickListener {
                    prefsAuth.logout()
                    startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
                    requireActivity().finishAffinity()
                }
                //  Set Resources for strings
                viewModel.updateChangeText(resources, requestCode)
                when (requestCode) {
                    1 -> {
                        viewModel.enterPin()
                    }
                    2 -> viewModel.launchBiometric()
                    3 -> viewModel.enterPin()
                    10, 11 -> viewModel.setPin()
                    else -> dismiss()
                }

            }
            viewModel.error.observe(viewLifecycleOwner) {
                showMsg(getString(it))
            }

            viewModel.let { vm ->
                vm.dropLoginOrPassword.observe(viewLifecycleOwner, { if (it) dismiss() })
                vm.clickListener.observe(viewLifecycleOwner, { click ->
                    when (click) {
                        20 -> onClickBiometricsData(activity)
                        //  Access allowed
                        200 -> {
                            when (requestCode) {
                                1, 2, 3 -> {
                                    myCallBack.invoke(true)
                                    isCancelTouch = false
                                    dismiss()
                                }
                                10, 11 -> {
                                    myCallBack.invoke(true)
                                    isCancelTouch = false
                                    dismiss()
                                }
                            }
                        }
                        777 -> {
                            myCallBack.invoke(true)
                            isCancelTouch = false
                            dismiss()
                        }
                        //  Access denied
                        400 -> Earthquake.onShake(requireActivity(), binding?.frame2)
                    }
                })
                vm.isLoader.observe(viewLifecycleOwner, { isLoader ->

                })
            }
            if (BiometricUtil.isBiometricReady(activity)) viewModel.isBiometric.set(true)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        if (isCancelTouch) myCallBack.invoke(false)
        super.onDismiss(dialog)
    }

    private fun onClickBiometricsData(fragmentActivity: androidx.fragment.app.FragmentActivity?) {
        fragmentActivity?.let { activity ->
            if (activity is androidx.appcompat.app.AppCompatActivity) {
                BiometricUtil.showBiometricPrompt(
                    activity = activity,
                    listener = this,
                    cryptoObject = null,
                    allowDeviceCredential = true
                )
            }
        }
    }

    override fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult) {
        Log.e("bio", "onBiometricAuthenticationSuccess")
        myCallBack.invoke(true)
        isCancelTouch = false
        dismiss()
    }

    override fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String) {
        Log.e("bio", "onBiometricAuthenticationError $errorCode $errorMessage")
        activity?.let {
            toast(it, errorMessage)
        }
    }
}
