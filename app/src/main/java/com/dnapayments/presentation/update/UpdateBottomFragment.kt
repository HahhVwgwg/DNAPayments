package com.dnapayments.presentation.update

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.dnapayments.R
import com.dnapayments.utils.base.BaseHomeBottomFragment

class UpdateBottomFragment(
    private val url: String, private val callBack: () -> Unit,
) :
    BaseHomeBottomFragment(R.layout.bottom_fragment_update) {

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        isCancelable = false
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        activity?.let {
            view?.run {
                findViewById<View>(R.id.update).setOnClickListener {
                    if (url.isNotEmpty()) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    callBack.invoke()
                }
            }
        }
    }
}