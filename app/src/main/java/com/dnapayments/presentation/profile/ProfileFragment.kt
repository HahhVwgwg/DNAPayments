package com.dnapayments.presentation.profile

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentProfileBinding
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import com.dnapayments.utils.dateFormat
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment :
    BaseBindingFragment<FragmentProfileBinding, MainViewModel>(R.layout.fragment_profile) {
    override val vm: MainViewModel by viewModel()
    private val prefsAuth: PrefsAuth by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            val user = prefsAuth.getUser()
            vm.login.set(user.login)
            vm.nameSurname.set("${user.name} ${user.surname}")
            vm.registrationDate.set(user.createdAt.dateFormat())
        }
    }
}