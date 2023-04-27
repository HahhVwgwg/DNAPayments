package com.dnapayments.utils

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.dnapayments.data.model.APIError
import com.dnapayments.domain.network.ResponseFB
import com.dnapayments.domain.network.SimpleResult
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.HttpException
import kotlin.coroutines.resume


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

suspend fun <T> Task<QuerySnapshot>.awaitWithList(
    data: Class<T>
): SimpleResult<ArrayList<T>> {
    var status: Boolean
    var message = ""
    val list = arrayListOf<T>()

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    status = true
                    for (document in task.result!!) try {
                        document.id
                        list.add(document.toObject(data))
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else -> {
                    status = false
                    task.exception?.localizedMessage?.let {
                        message = it
                        println(message)
                    }
                }
            }
            cont.resume(
                ResponseFB(
                    status = status,
                    message = message,
                    data = list
                ).getResponseFB()
            )
        }
    }
}

fun <T> Throwable.simpleError(): SimpleResult<T> {
    this.printStackTrace()
    return when (this) {
        is HttpException -> {
            val message: APIError = Gson().fromJson(
                this.response()?.errorBody()?.charStream(),
                APIError::class.java
            )
            message.error()?.let {
                SimpleResult.Error(it)
            } ?: SimpleResult.Error("Что-то пошло не так")
        }
        is IllegalStateException -> {
            SimpleResult.Error("Что-то пошло не так")
        }
        else -> {
            SimpleResult.NetworkError
        }
    }
}
