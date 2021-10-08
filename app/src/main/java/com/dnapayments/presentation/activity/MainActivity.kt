package com.dnapayments.presentation.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dnapayments.R
import com.dnapayments.databinding.ActivityMainBinding
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.utils.base.BaseBindingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val vm: MainViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            navView.setOnItemSelectedListener {
                if (it.itemId == R.id.navigation_phone) {
                    startCall()
                    true
                } else {
                    false
                }
            }
            navView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
        }
    }

    override fun onBackPressed() {
        binding?.navView?.let {
            when (NavHostFragment.findNavController(supportFragmentManager.fragments[0]).currentDestination?.id) {
                R.id.navigation_add_card,
                R.id.navigation_history,
                -> {
                    vm.viewState.set(true)
                }
                else -> {

                }
            }
            super.onBackPressed()
        }
    }

    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:" + getString(R.string.phone_number))
        startActivity(callIntent)
    }
}