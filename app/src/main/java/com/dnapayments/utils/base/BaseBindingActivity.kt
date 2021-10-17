package com.dnapayments.utils.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dnapayments.R

abstract class BaseBindingActivity<B : ViewDataBinding>(@LayoutRes private val layoutResID: Int) :
    BaseActivity() {

    companion object {
//        const val TAG = "BaseBindingActivity"
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    //  Your view data binding
    var binding: B? = null

    override fun onCreateUI(savedInstanceState: Bundle?) {
        //  Override Resources ID Layout
        binding = DataBindingUtil.setContentView(this, layoutResID)
        //  Initialize all widget in layout by ID
        initViews(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (binding != null) binding = null
    }

    private var dialogForLoader: Dialog? = null

    fun showLoader() {
        this.let {
            killDialog()
            dialogForLoader = Dialog(this)
            dialogForLoader?.let { dialog ->
                dialog.setContentView(R.layout.loader)
                dialog.setCancelable(false)
                dialog.setCanceledOnTouchOutside(false)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
            }
        }
    }

    fun hideLoader() {
        dialogForLoader?.dismiss()
        killDialog()
    }

    private fun killDialog() {
        if (dialogForLoader != null)
            dialogForLoader = null
    }
}