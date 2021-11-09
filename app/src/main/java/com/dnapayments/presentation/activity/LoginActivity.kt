package com.dnapayments.presentation.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.databinding.ActivityLoginBinding
import com.dnapayments.utils.base.BaseBindingActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity :
    BaseBindingActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private val vm: LoginViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.region_secondary)
        binding?.apply {
            viewModel = vm
            join.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegistrationActivity::class.java))
                overridePendingTransition(R.anim.slide_up, R.anim.no_transition)
                finishAffinity()
            }
            vm.success.observe(this@LoginActivity, {
                if (it) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }
            })
            vm.error.observe(this@LoginActivity, {
                showAlert(getStr(it))
            })
            vm.errorString.observe(this@LoginActivity, {
                showAlert(it)
            })
            vm.isLoading.observe(this@LoginActivity, {
                if (it) {
                    showLoader()
                } else {
                    hideLoader()
                }
            })
            vm.showNetworkError.observe(this@LoginActivity, {
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
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            phoneNumberWithCountryCode,
                            message
                        )
                    )
                )
            )
        } else {
            vm.error.value = R.string.not_found_whatsapp
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