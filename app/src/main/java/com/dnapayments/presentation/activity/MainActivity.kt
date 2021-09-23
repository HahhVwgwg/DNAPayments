package com.dnapayments.presentation.activity

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dnapayments.R
import com.dnapayments.databinding.ActivityMainBinding
import com.dnapayments.utils.base.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            navView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
        }
    }
}