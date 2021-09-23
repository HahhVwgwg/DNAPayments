package com.dnapayments.utils

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes


fun Context.makeToast(message: String) {
    if (message.isNotBlank())
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.getStyledResourceId(id: Int): Int {
    val attrs = intArrayOf(id)
    val typedArray = this.theme.obtainStyledAttributes(attrs)
    return typedArray.getResourceId(0, android.R.color.black)
}

internal fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun String?.emailIsValid(): Boolean {
    if (this == null) {
        return false
    }
    return !TextUtils.isEmpty(this.trim()) && android.util.Patterns.EMAIL_ADDRESS.matcher(this.trim())
        .matches()
}

