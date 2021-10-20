package com.dnapayments.presentation.knowledge

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.databinding.FragmentKnowledgeBinding
import com.dnapayments.utils.Constants.ITEM
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class KnowledgeFragment :
    BaseBindingFragment<FragmentKnowledgeBinding, KnowledgeViewModel>(R.layout.fragment_knowledge) {
    private var adapter: KnowledgeAdapter? = null
    override val vm: KnowledgeViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            back.setOnClickListener {
                onBackPressed()
            }
            adapter = KnowledgeAdapter {
                findNavController().navigate(R.id.action_knowledge_to_knowledge_details,
                    Bundle().apply {
                        putParcelable(ITEM, it)
                    })
            }
            recyclerView.adapter = adapter
            refreshSwipe.setOnRefreshListener {
                vm.fetchKnowBase()
            }
            vm.knowledgeList.observe(viewLifecycleOwner, {
                adapter?.setData(it)
            })
            vm.success.observe(viewLifecycleOwner, {
                refreshSwipe.isRefreshing = !it
            })
        }
    }
}