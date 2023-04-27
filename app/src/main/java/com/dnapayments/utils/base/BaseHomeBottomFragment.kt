package com.dnapayments.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.dnapayments.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseHomeBottomFragment(@LayoutRes private val layoutResID: Int) :
    BottomSheetDialogFragment() {

    companion object {
        const val TAG = "BaseBottomSheetDialogFragment"
    }

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onStart() {
        super.onStart()
        dialog?.also {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.peekHeight = 900
            view?.requestLayout()
        }
    }

    //  Initialize your views and start code
    protected abstract fun initViews(view: View?, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layoutResID, container, false)
    }

    //  Initialize all widget in layout by ID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        initViews(view, savedInstanceState)
}