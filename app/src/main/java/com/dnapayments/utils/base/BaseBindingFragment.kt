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
import com.dnapayments.utils.PrefsAuth

abstract class BaseBindingFragment<B : ViewDataBinding, T : BaseViewModel>(@LayoutRes private val layoutResID: Int) :
    BaseFragment() {

    companion object {
        const val TAG = "BaseBindingFragment"
    }

    //  Your view data binding
    var binding: B? = null
    protected abstract val vm: BaseViewModel

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
        initViews(savedInstanceState)
        vm.error.observe(viewLifecycleOwner) {
            showAlert(getStr(it))
        }
        vm.errorString.observe(viewLifecycleOwner) {
            if (it == "Сіздің қосымшаға кіру уақытыңыз бітті. Қайта кіруіңізді сұраймыз.") {
                showAlert(it) {
                    logout()
                }
            } else {
                showAlert(it)
            }
        }
        vm.showNetworkError.observe(viewLifecycleOwner) {
            showAlert(getStr(R.string.check_internet_connection))
        }
    }

    private fun logout() {
        val prefsAuth = PrefsAuth(requireContext())
        prefsAuth.logout()
        startActivity(Intent(requireActivity(), RegistrationActivity::class.java))
        requireActivity().finishAffinity()
    }

    private var isShowMsg = false

    private fun showAlert(msg: String) {
        if (isShowMsg) return
        Dialog(requireContext()).apply {
            this.setContentView(R.layout.layout_alert)
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

    private fun showAlert(msg: String, myCallBack: () -> Unit) {
        if (isShowMsg) return
        Dialog(requireContext()).apply {
            this.setContentView(R.layout.layout_alert)
            this.setCancelable(false)
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.setCanceledOnTouchOutside(false)
            this.findViewById<Button>(R.id.btnClose).setOnClickListener {
                isShowMsg = false
                myCallBack.invoke()
                dismiss()
            }
            this.findViewById<android.widget.TextView>(R.id.dialogContent).text = msg
            this.show()
            isShowMsg = true
        }
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
}