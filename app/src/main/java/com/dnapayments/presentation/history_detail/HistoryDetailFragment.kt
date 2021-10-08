package com.dnapayments.presentation.history_detail

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentHistoryTransactionBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDetailFragment :
    BaseBindingFragment<FragmentHistoryTransactionBinding, HistoryDetailViewModel>(R.layout.fragment_history_transaction) {
    override val vm: HistoryDetailViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        val id = arguments?.getInt("id") ?: -1
        binding?.apply {
            viewModel = vm
            vm.fetchTransactionById(id)
            back.setOnClickListener {
                onBackPressed()
            }
        }
    }
}