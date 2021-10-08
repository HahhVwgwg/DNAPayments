package com.dnapayments.presentation.registration

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentAuthorizationBinding
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AuthorizationFragment :
    BaseBindingFragment<FragmentAuthorizationBinding, AuthorizationViewModel>(R.layout.fragment_authorization) {
    override val vm: AuthorizationViewModel by sharedViewModel()
    private val prefs: PrefsAuth by inject()

    override fun initViews(savedInstanceState: Bundle?) {
        binding?.run {
            prefs.logout()
            viewModel = vm
            generateBtn.setOnClickListener {
                val phoneNumber = phoneNumberText.text.toString()
                val completePhoneNumber = "7$phoneNumber"
                prefs.saveUserPhone(completePhoneNumber)
                vm.getOtpByPhoneNumber(completePhoneNumber)
            }
            vm.otpResponse.observe(viewLifecycleOwner, {
                findNavController().navigate(R.id.action_initialFragment_to_mobizonFragment,
                    Bundle().apply {
                        putParcelable("otp", it)
                        putString("phone", prefs.getUserPhone())
                    })
            })
        }

    }
}