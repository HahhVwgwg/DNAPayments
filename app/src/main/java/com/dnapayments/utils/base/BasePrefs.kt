package com.dnapayments.utils.base

import android.content.Context
import android.content.SharedPreferences

abstract class BasePrefs(context: Context, prefsName: String) {

    var prefs: SharedPreferences? = null

    init {
        prefs = context.getSharedPreferences(prefsName,  Context.MODE_PRIVATE)
    }
}