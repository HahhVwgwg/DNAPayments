package com.dnapayments.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.databinding.ActivityRegistrationBinding
import com.dnapayments.utils.base.BaseBindingActivity

class RegistrationActivity :
    BaseBindingActivity<ActivityRegistrationBinding>(R.layout.activity_registration) {
    override fun initViews(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.region_secondary)
        binding?.apply {
            join.setOnClickListener {
                //ToDo open Whatsapp with number send with text
            }
            register.setOnClickListener {
                if (password.text.isEmpty() || login.text.isEmpty()) {
                    showAlert(getStr(R.string.fill_fields))
                } else {
                    //ToDo save UserIsRegisteredLocally
                    startActivity(Intent(this@RegistrationActivity, MainActivity::class.java))
                }
            }
        }
    }

}