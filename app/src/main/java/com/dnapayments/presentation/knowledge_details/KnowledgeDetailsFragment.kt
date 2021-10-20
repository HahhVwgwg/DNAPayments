package com.dnapayments.presentation.knowledge_details

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.databinding.FragmentKnowledgeDetailsBinding
import com.dnapayments.utils.Constants.ITEM
import com.dnapayments.utils.base.BaseBindingFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class KnowledgeDetailsFragment :
    BaseBindingFragment<FragmentKnowledgeDetailsBinding, KnowledgeDetailsViewModel>(R.layout.fragment_knowledge_details) {
    override val vm: KnowledgeDetailsViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            vm.knowledgeDetails.set(arguments?.getParcelable(ITEM))
            back.setOnClickListener {
                onBackPressed()
            }
            lifecycle.addObserver(youtubePlayerView)
            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    arguments?.getParcelable<NotificationElement>(ITEM)?.let {
                        youTubePlayer.loadVideo(it.image, 0f)
                    }
                }
            })
        }
    }
}