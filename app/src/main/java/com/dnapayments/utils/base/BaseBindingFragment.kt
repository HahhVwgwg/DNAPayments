package com.dnapayments.utils.base

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.dnapayments.R
import com.dnapayments.presentation.activity.MainActivity
import com.dnapayments.presentation.activity.RegistrationActivity

abstract class BaseBindingFragment<B : ViewDataBinding, T : BaseViewModel>(@LayoutRes private val layoutResID: Int) :
    BaseFragment() {

    companion object {
        const val TAG = "BaseBindingFragment"
    }

    protected abstract val vm: BaseViewModel

    //  Your view data binding
    var binding: B? = null

    //two error dialog handle
    private var isShowMsg = false

    //  Bind all widgets and start code
    protected abstract fun initViews(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        return binding?.root
    }

    // Initialize all widget in layout by ID
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                showLoader()
            } else {
                hideLoader()
            }
        }
        vm.error.observe(viewLifecycleOwner) {
            showMsg(getStr(it))
        }
        vm.errorString.observe(viewLifecycleOwner) {
            if (it == "token_invalid" || it == "token_absent") {
                logout()
            } else {
                showMsg(it)
            }
        }
        vm.showNetworkError.observe(viewLifecycleOwner) {
            showMsg(getStr(R.string.no_connection))
        }
        initViews(savedInstanceState)
    }

    private fun logout() {
        startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
        requireActivity().finishAffinity()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        if (binding != null)
            binding = null
    }

    fun onBackPressed() {
        if (activity is MainActivity) {
            (activity as MainActivity).onBackPressed()
        }
    }

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
}