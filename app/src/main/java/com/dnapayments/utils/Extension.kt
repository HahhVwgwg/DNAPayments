package com.dnapayments.utils

import android.content.Context
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = textPaint.linkColor
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        if (startIndexOfLink == -1) continue
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

@Suppress("DEPRECATION")
fun String.fromHtml(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}
