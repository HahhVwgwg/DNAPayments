package com.dnapayments.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.dnapayments.data.model.APIError
import com.dnapayments.data.model.SimpleResult
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat


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

fun <T> Throwable.simpleError(): SimpleResult<T> {
    this.printStackTrace()
    return when (this) {
        is HttpException -> {
            val message: APIError = Gson().fromJson(this.response()?.errorBody()?.charStream(),
                APIError::class.java)
            message.message()?.let {
                SimpleResult.Error(it)
            } ?: SimpleResult.Error("")
        }
        is JsonSyntaxException -> {
            SimpleResult.Error("Что-то пошло не так")
        }
        is IOException -> {
            SimpleResult.Error("Нет соединения")
        }
        else -> {
            SimpleResult.NetworkError
        }
    }
}

fun String?.emailIsValid(): Boolean {
    if (this == null) {
        return false
    }
    return !TextUtils.isEmpty(this.trim()) && android.util.Patterns.EMAIL_ADDRESS.matcher(this.trim())
        .matches()
}


@SuppressLint("SimpleDateFormat")
fun String.dateFormat(): String {
    return SimpleDateFormat("dd.MM.yyyy").format(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(
        this.substring(0, 19))!!)
}