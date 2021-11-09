package com.dnapayments.presentation.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentSettingsBinding
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.presentation.activity.LoginActivity
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding, MainViewModel>(R.layout.fragment_settings) {
    override val vm: MainViewModel by viewModel()
    private val prefsAuth: PrefsAuth by inject()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.run {
            logout.setOnClickListener {
                prefsAuth.logout()
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finishAffinity()
            }
            rateUs.name.setOnClickListener {
                launchMarket()
            }

            shareApp.name.setOnClickListener {
                shareApp()
            }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Miras")
            var shareMessage = "\nСізге осы қосымшаны ұсынуға рұқсат етіңіз\n\n"
            shareMessage =
                """
                        ${shareMessage}https://play.google.com/store/apps/details?id=${requireActivity().packageName}
                        
                        
                        """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Таңдаңыз"))
        } catch (e: Exception) {
        }
    }

    private fun launchMarket() {
        val uri: Uri = Uri.parse("market://details?id=" + requireActivity().packageName)
        val myAppLinkToMarket = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(myAppLinkToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + requireActivity().packageName)))
        }
    }
}