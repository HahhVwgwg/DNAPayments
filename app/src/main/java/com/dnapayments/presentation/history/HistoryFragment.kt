package com.dnapayments.presentation.history

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentHistoryBinding
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment :
    BaseBindingFragment<FragmentHistoryBinding, MainViewModel>(R.layout.fragment_history) {
    private var adapter: HistoryAdapter? = null
    override val vm: HistoryViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            adapter = HistoryAdapter {
                findNavController().navigate(R.id.action_history_to_detailed, Bundle().apply {
                    putInt("id", it.id.toInt())
                })
            }
            recyclerView.adapter = adapter
            refreshSwipe.setOnRefreshListener {
                vm.fetchHistory()
            }
            vm.walletTransactions.observe(viewLifecycleOwner, {
                adapter?.setData(it)
            })
            vm.success.observe(viewLifecycleOwner, {
                refreshSwipe.isRefreshing = !it

            })
        }
    }
}