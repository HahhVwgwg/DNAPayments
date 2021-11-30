package com.dnapayments.presentation.profile

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentProfileBinding
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseBindingFragment<FragmentProfileBinding, MainViewModel>(R.layout.fragment_profile) {
    override val vm: MainViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            vm.login.set("Youtube")
            vm.nameSurname.set("Maksat Bazarbayev")
            vm.registrationDate.set("29.11.2021")
        }
    }
}