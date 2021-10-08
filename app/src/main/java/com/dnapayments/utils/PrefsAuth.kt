package com.dnapayments.utils

import android.content.Context
import com.dnapayments.utils.base.BasePrefs

/*  Storage for token, pin and other user info data  */
class PrefsAuth(context: Context) : BasePrefs(context, PREFS_AUTH) {

    companion object {
        private const val TAG = "PrefsAuth"
        private const val PREFS_AUTH = "prefsAuth"
        private const val IS_FIRST = "isFirst"
        private const val IS_AUTHORIZED = "isAuthorized"
        private const val LOGIN = "login"
        private const val FULL_NAME = "fullName"
        private const val DEVICE_ID = "deviceID"
        private const val AUTH_TOKEN = "authToken"
        private const val ACCESS_TOKEN = "accessToken"
        private const val PIN = "pin"
        private const val IS_PIN = "isPin"
        private const val PRODUCT = "product"
        private const val USER_PHONE_NUMBER: String = "USER_PHONE_NUMBER"

        //  TEST
        private const val PASSWORD = "password"
    }

    fun isFirst(): Boolean = prefs?.getBoolean(IS_FIRST, true) ?: true


    fun isPinCodeExist(): Boolean = prefs?.getBoolean(IS_PIN, false) ?: false
    fun savePin(pin: String) = prefs?.edit()?.putString(PIN, pin)?.putBoolean(IS_PIN, true)?.apply()
    fun saveUserPhone(phone: String) =
        prefs?.edit()?.putString(USER_PHONE_NUMBER, phone)?.apply()

    fun saveDeviceId(deviceId: String) =
        prefs?.edit()?.putString(DEVICE_ID, deviceId)?.apply()

    fun saveAuthToken(authToken: String) =
        prefs?.edit()?.putString(AUTH_TOKEN, authToken)?.apply()

    fun saveAccessToken(accessToken: String) =
        prefs?.edit()?.putString(ACCESS_TOKEN, accessToken)?.apply()

    fun loadPin(): String? = prefs?.getString(PIN, null)
    fun getUserPhone(): String? = prefs?.getString(USER_PHONE_NUMBER, null)
    fun getAccessToken(): String? = prefs?.getString(ACCESS_TOKEN, null)
    fun getAuthToken(): String? = prefs?.getString(AUTH_TOKEN, null)
    fun getDeviceId(): String? = prefs?.getString(DEVICE_ID, null)

    fun disablePin() {
        prefs?.let { pref ->
            pref.edit()
                .putBoolean(IS_AUTHORIZED, false)
                .putString(PIN, null)
        }
    }

    fun getLogin(): String? = prefs?.getString(LOGIN, null)
    fun getPassword(): String? = prefs?.getString(PASSWORD, null)

    //  Get full name user
    fun getFullName(): String = prefs?.getString(FULL_NAME, "") ?: ""

    //  Check, if user authorized or not
    fun isAuthorized(): Boolean = prefs?.getBoolean(IS_AUTHORIZED, false) ?: false
    fun setAuthorized(isAuthorized: Boolean) =
        prefs?.edit()?.putBoolean(IS_AUTHORIZED, isAuthorized)?.apply()

    fun logout() = prefs?.edit()?.clear()?.apply()
    fun clearToken() = prefs?.edit()?.putString(ACCESS_TOKEN, "")?.apply()
}