package com.dnapayments.presentation.withdraw

import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentWithdrawBinding
import com.dnapayments.presentation.choose_card.MultiChoiceBottomFragment
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.presentation.pin_code.PinCodeFragment
import com.dnapayments.utils.base.BaseBindingFragment
import com.dnapayments.utils.showKeyboard
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WithdrawFragment :
    BaseBindingFragment<FragmentWithdrawBinding, MainViewModel>(R.layout.fragment_withdraw) {
    override val vm: MainViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            viewModel = vm
            refreshSwipe.setOnRefreshListener {
                vm.fetchProfile()
            }
            refresh.setOnClickListener {
                vm.fetchProfile()
            }
            myBalanceHistory.setOnClickListener {
                findNavController().navigate(R.id.action_withdraw_to_history)
            }
            vm.showBottomSheet.observe(viewLifecycleOwner, {
                PinCodeFragment.newInstance(3).show(
                    parentFragmentManager,
                    null
                )
            })

            vm.onPinCallback.observe(viewLifecycleOwner, {
                if (it) {
                    vm.withdraw()
                }
            })
            vm.cards.observe(viewLifecycleOwner, { cardList ->
                MultiChoiceBottomFragment({
                    vm.selectedCard.set(it.lastFour.toString())
                    vm.cardId.set(it.id)
                }, {
                    vm.deleteCard(it.id)
                }, {
                    if (it) {
                        findNavController().navigate(R.id.action_withdraw_to_add_card)
                        vm.viewState.set(false)
                    }
                }, cardList).show(parentFragmentManager, null)
            })
            container.setOnClickListener {
                withDrawAmount.requestFocus()
                withDrawAmount.setSelection(withDrawAmount.text.length)
                withDrawAmount.showKeyboard()
            }
            vm.success.observe(viewLifecycleOwner, {
                refreshSwipe.isRefreshing = !it
            })
        }
    }
}