package com.dnapayments.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.databinding.ActivityRegistrationBinding
import com.dnapayments.utils.base.BaseBindingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationActivity :
    BaseBindingActivity<ActivityRegistrationBinding>(R.layout.activity_registration) {

    private val vm: LoginViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.region_secondary)
        binding?.apply {
            viewModel = vm
            join.setOnClickListener {
                sendWhatsappSms()
            }
            vm.success.observe(this@RegistrationActivity, {
                if (it) {
                    startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                    finishAffinity()
                }
            })
            vm.error.observe(this@RegistrationActivity, {
                showAlert(getStr(it))
            })
            vm.errorString.observe(this@RegistrationActivity, {
                showAlert(it)
            })
            vm.isLoading.observe(this@RegistrationActivity, {
                if (it) {
                    showLoader()
                } else {
                    hideLoader()
                }
            })
            vm.showNetworkError.observe(this@RegistrationActivity, {
                showAlert(getStr(R.string.check_internet_connection))
            })
        }
    }

    private fun sendWhatsappSms() {
        val isWhatsappInstalled: Boolean = whatsappInstalledOrNot()
        if (isWhatsappInstalled) {
            val phoneNumberWithCountryCode = "+77789244399"
            val message = "Сәлеметсіз бе! Мек курсқа жазылғым келеді. Қалай жазылсам болады?"

            startActivity(
                Intent(Intent.ACTION_VIEW,
                    Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                        phoneNumberWithCountryCode,
                        message))
                )
            )
        } else {
            vm.error.value = R.string.not_found_whatsapp
            val uri = Uri.parse("market://details?id=com.whatsapp")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri)
            startActivity(goToMarket)
        }
    }

    private fun whatsappInstalledOrNot(): Boolean {
        return try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}