package com.dnapayments.presentation.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dnapayments.R
import com.dnapayments.databinding.ActivityMainBinding
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val prefsAuth: PrefsAuth by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mainLayout.setBackgroundColor(
                    if (prefsAuth.isFirst()) getColor(R.color.white) else getColor(
                        R.color.purple_200
                    )
                )
            }
            navView.setupWithNavController(findNavController(R.id.nav_host_fragment_main))
        }
    }

    fun toggleVisibility(value: Boolean) {
        binding?.navView?.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
//        binding?.navView?.let {
//            when (NavHostFragment.findNavController(supportFragmentManager.fragments[0]).currentDestination?.id) {
//                R.id.navigation_quiz,
//                R.id.navigation_result,
//                -> {
//                    toggleVisibility(true)
//                    super.onBackPressed()
//                }
//                R.id.navigation_details,
//                -> {
//
//                }
//                else -> {
//                    super.onBackPressed()
//                }
//            }
//
//        }
        onBack()
    }

    fun onBack() {
        super.onBackPressed()
    }
}