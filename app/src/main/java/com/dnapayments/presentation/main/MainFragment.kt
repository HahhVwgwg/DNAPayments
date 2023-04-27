package com.dnapayments.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.dnapayments.R
import com.dnapayments.data.model.Zhyrau
import com.dnapayments.databinding.FragmentMainBinding
import com.dnapayments.domain.presentation.Story
import com.dnapayments.presentation.activity.StoryActivity
import com.dnapayments.presentation.details.DetailsFragment.Companion.ZHYRAU
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment :
    BaseBindingFragment<FragmentMainBinding>(R.layout.fragment_main) {

    private var storyAdapter: StoryAdapter = StoryAdapter(arrayListOf()) { storyList, position ->
        val intent = Intent(context, StoryActivity::class.java)
        intent.putParcelableArrayListExtra(StoryActivity.STORIES, storyList)
        intent.putExtra(StoryActivity.CURRENT_POSITION, position)
        startActivity(intent)
        requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_stay)
    }

    private val zhyrauAdapter = ZhyrauAdapter {
        onClickZhyrau(it)
    }


    private val vm: MainViewModel by sharedViewModel()

    override fun initViews(savedInstanceState: Bundle?) {
        initRecyclerView()
        initRecyclerViewZhyrau()
        vm.zhyrauList.observe(viewLifecycleOwner) {
            zhyrauAdapter.setData(it)
        }
    }

    private fun initRecyclerViewZhyrau() = with(binding) {
        zhyrauRecyclerView.adapter = zhyrauAdapter
    }

    private fun initRecyclerView() = with(binding) {
        recyclerView.adapter = storyAdapter
        val list = listOf(
            Story(
                id = "1",
                "https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/2.png?alt=media&token=3f7a9aea-d5e4-40f7-84c2-01457e837eec",
                "Қош келдіңіздер"
            ),
            Story(
                id = "2",
                "https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/1.png?alt=media&token=6f05101f-2187-47c6-9b6d-1dbd73a3de4c",
                "Функциялар"
            ),

            )
        storyAdapter.setStories(list, vm.storiesShownList(list))
    }

    private fun onClickZhyrau(zhyrau: Zhyrau) {
        findNavController().navigate(R.id.action_main_to_details, bundleOf(ZHYRAU to zhyrau))
    }
}