package com.dnapayments.presentation.settings

import android.content.Intent
import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentSettingsBinding
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding, MainViewModel>(R.layout.fragment_settings) {
    override val vm: MainViewModel by viewModel()
    private val prefsAuth: PrefsAuth by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            rateUs.name2.setOnClickListener {
                launchMarket()
            }

            shareApp.name2.setOnClickListener {
                shareApp()
            }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Miras")
            var shareMessage = "\nLet me send this application\n\n"
            shareMessage =
                """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${requireActivity().packageName}
                        
                        
                        """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Choose"))
        } catch (e: Exception) {
        }
    }

    private fun launchMarket() {
        prefsAuth.setFirst(!prefsAuth.isFirst())
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }
}