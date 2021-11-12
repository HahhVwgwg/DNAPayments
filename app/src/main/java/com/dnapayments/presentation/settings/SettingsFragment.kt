package com.dnapayments.presentation.settings

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentSettingsBinding
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding, MainViewModel>(R.layout.fragment_settings) {
    override val vm: MainViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {

        }
    }
}