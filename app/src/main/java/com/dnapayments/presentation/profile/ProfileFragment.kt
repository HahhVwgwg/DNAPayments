package com.dnapayments.presentation.profile

import android.content.Intent
import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentProfileBinding
import com.dnapayments.presentation.activity.RegistrationActivity
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ProfileFragment :
    BaseBindingFragment<FragmentProfileBinding, MainViewModel>(R.layout.fragment_profile) {
    private val prefsAuth: PrefsAuth by inject()
    override val vm: MainViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            logOutBtn.setOnClickListener {
                prefsAuth.logout()
                startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
                requireActivity().finishAffinity()
            }
            changePinCode.setOnClickListener {
                vm.getNews()
            }
        }
    }
}