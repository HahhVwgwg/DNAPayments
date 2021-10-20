package com.dnapayments.utils.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dnapayments.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBindingBottomFragment<B : ViewDataBinding>(@LayoutRes private val layoutResID: Int) :
    BottomSheetDialogFragment() {

    //  Your view data binding
    var binding: B? = null

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }
    
    //two error dialog handle
    private var isShowMsg = false

    fun showMsg(msg: String) {
        if (isShowMsg) return
        Dialog(requireContext()).apply {
            this.setContentView(R.layout.layout_msg)
            this.setCancelable(false)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(false)
            this.findViewById<Button>(R.id.btnClose).setOnClickListener {
                isShowMsg = false
                dismiss()
            }
            this.findViewById<android.widget.TextView>(R.id.dialogContent).text = msg
            this.show()
            isShowMsg = true
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { layout ->
                val behaviour = BottomSheetBehavior.from(layout)
                setupFullHeight(layout)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    //  Initialize your views and start code
    protected abstract fun initViews(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding?.root
    }

    //  Initialize all widget in layout by ID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        initViews(savedInstanceState)

    /*  Modal windows  */
    open fun toast(context: Context? = null, msg: Any, isDuration: Boolean = false) {
        val duration = if (isDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        context?.let {
            when (msg) {
                is String -> Toast.makeText(context, msg, duration).show()
                is Int -> Toast.makeText(context, msg, duration).show()
            }
        } ?: activity?.let {
            when (msg) {
                is String -> Toast.makeText(it, msg, duration).show()
                is Int -> Toast.makeText(it, msg, duration).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (binding != null) binding = null
    }
}