package com.dnapayments.utils

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.dnapayments.R
import com.dnapayments.data.Resource
import com.dnapayments.data.model.APIError
import com.dnapayments.data.model.SimpleResult
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


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

fun <T> Throwable.simpleError(): Resource<T> {
    this.printStackTrace()
    return when (this) {
        is HttpException -> {
            val message: APIError = Gson().fromJson(this.response()?.errorBody()?.charStream(),
                APIError::class.java)
            message.error()?.let {
                Resource.error(it)
            } ?: Resource.error("")
        }
        is IOException -> {
            Resource.error(R.string.check_internet_connection)
        }
        else -> {
            Resource.error(R.string.something_went_wrong)
        }
    }
}

fun <T> Throwable.simpleErrorSecond(): SimpleResult<T> {
    this.printStackTrace()
    return when (this) {
        is HttpException -> {
            val message: APIError = Gson().fromJson(this.response()?.errorBody()?.charStream(),
                APIError::class.java)
            message.error()?.let {
                SimpleResult.Error(it)
            } ?: SimpleResult.Error("")
        }
        is IOException -> {
            SimpleResult.Error("Нет соединения")
        }
        else -> {
            SimpleResult.NetworkError
        }
    }
}

fun Int.sendFormat(): String {
    return "Перевести $this \u20b8"
}

fun String.dateFormat(): String {
    return SimpleDateFormat(
        "HH:mm",
        Locale.getDefault()
    ).format(
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(
            this
        ) ?: Date()
    )
}

fun String.dateFormatSecond(): String {
    return SimpleDateFormat(
        "dd/MM/yyyy",
        Locale.getDefault()
    ).format(
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(
            this
        ) ?: Date()
    )
}

fun String.isInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: Exception) {
        false
    }
}

fun Int.commissionFormat(): String {
    return "Комиссия $this \u20b8"
}

fun EditText.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

