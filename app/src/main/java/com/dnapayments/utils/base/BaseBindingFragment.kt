package com.dnapayments.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dnapayments.presentation.activity.MainActivity

abstract class BaseBindingFragment<B : ViewDataBinding>(@LayoutRes private val layoutResID: Int) :
    BaseFragment() {

    companion object {
        const val TAG = "BaseBindingFragment"
    }

    //  Your view data binding
    lateinit var binding: B

    //  Bind all widgets and start code
    protected abstract fun initViews(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding.root
    }

    // Initialize all widget in layout by ID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) =
        initViews(savedInstanceState)

    fun onBackPressed() {
        if (activity is MainActivity) {
            (activity as MainActivity).onBackPressed()
        }
    }
}