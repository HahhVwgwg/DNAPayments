package com.dnapayments.presentation.history_detail

import android.os.Bundle
import androidx.core.content.ContextCompat
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
            vm.status.observe(viewLifecycleOwner, {
                when (it) {
                    "pending" -> {
                        status.text = "В ожидании"
                        status.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    "invalid_card" -> {
                        status.text = "Неверная карта"
                        status.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    "processed" -> {
                        status.text = "Завершен"
                        status.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
                    }
                    "cancelled" -> {
                        status.text = "Отменен"
                        status.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                    else -> {
                        status.text = "Ошибка"
                        status.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                    }
                }
            })
        }
    }
}