package com.dnapayments.presentation.add_card

import android.os.Bundle
import android.view.WindowManager
import com.dnapayments.R
import com.dnapayments.databinding.FragmentAddCardBinding
import com.dnapayments.utils.FourDigitCardFormatWatcher
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCardFragment :
    BaseBindingFragment<FragmentAddCardBinding, AddCardViewModel>(R.layout.fragment_add_card) {
    override val vm: AddCardViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            cardNumber.addTextChangedListener(FourDigitCardFormatWatcher())
            cardNumberRepeat.addTextChangedListener(FourDigitCardFormatWatcher())
            vm.success.observe(viewLifecycleOwner, {
                if (it) onBackPressed()
            })
            back.setOnClickListener {
                onBackPressed()
            }
        }
    }
}