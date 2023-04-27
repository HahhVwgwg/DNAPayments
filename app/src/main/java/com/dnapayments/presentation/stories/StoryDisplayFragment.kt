package com.dnapayments.presentation.stories

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.dnapayments.R
import com.dnapayments.domain.presentation.Story
import com.dnapayments.presentation.activity.StoryActivity
import kotlinx.android.synthetic.main.fragment_story_display.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoryDisplayFragment : Fragment(), StoriesProgressView.StoriesListener {

    private val TAG = javaClass.name

    private val vm: StoryViewModel by viewModel()

    companion object {
        private const val EXTRA_POSITION = "EXTRA_POSITION"
        private const val EXTRA_STORY_USER = "EXTRA_STORY_USER"
        fun newInstance(position: Int, story: Story): StoryDisplayFragment {
            return StoryDisplayFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_POSITION, position)
                    putParcelable(EXTRA_STORY_USER, story)
                }
            }
        }
    }

    private val position: Int by
    lazy { arguments?.getInt(EXTRA_POSITION) ?: 0 }

    private val storyUser: Story by
    lazy {
        (arguments?.getParcelable<Story>(
            EXTRA_STORY_USER
        ) as Story)
    }

    private val storyImage: String by
    lazy { storyUser.image }

    private var counter = 0
    private var pressTime = 0L
    private var limit = 500L
    private var pageViewOperator: PageViewOperator? = null
    private var onResumeCalled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_story_display, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        updateStory()
        close_story.setOnClickListener {
            pageViewOperator?.closeStory()
        }

        storiesProgressView?.setStoriesListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        pageViewOperator = context as PageViewOperator
    }

    override fun onStart() {
        super.onStart()
        counter = restorePosition()
    }

    override fun onResume() {
        super.onResume()
        detectCurrentStoryAndSaveToLocalStorage()
        onResumeCalled = true
        storiesProgressView?.setStoriesCountDebug(
            1, position = arguments?.getInt(EXTRA_POSITION) ?: -1
        )
        storiesProgressView?.setAllStoryDuration(4000L)
        counter = StoryActivity.progressState.get(arguments?.getInt(EXTRA_POSITION) ?: 0)
        storiesProgressView?.startStories(counter)
    }

    override fun onPause() {
        super.onPause()
        storiesProgressView?.abandon()
    }

    private fun detectCurrentStoryAndSaveToLocalStorage() {
        vm.saveStoriesShownById(storyUser.id)
        println(
            "Stories position:${storyUser.id} $position $counter ${
                vm.isStoriesShownToUser(
                    storyUser.id
                )
            }"
        )
    }

    private fun updateStory() {
        if (isResumed) {
            detectCurrentStoryAndSaveToLocalStorage()
        }
        Glide.with(this)
            .load(storyUser.image)
            .fitCenter()
            .into(storyDisplayImage)
    }

    override fun onNext() {
        if (1 <= counter + 1) {
            return
        }

        ++counter
        savePosition(counter)
        updateStory()
    }

    override fun onPrev() {
        if (counter - 1 < 0) return

        --counter
        savePosition(counter)
        updateStory()
    }

    override fun onComplete() {
        pageViewOperator?.nextPageView()
    }

    private fun restorePosition(): Int {
        return StoryActivity.progressState.get(position)
    }

    private fun savePosition(pos: Int) {
        StoryActivity.progressState.put(position, pos)
    }

    private fun setUpUi() {

        val touchListener = object : OnSwipeTouchListener(requireActivity()) {

            override fun onSwipeBottom() {
                pageViewOperator?.closeStory()
            }

            override fun onSwipeTop() {
                Toast.makeText(requireContext(), "onSwipeTop", Toast.LENGTH_SHORT).show()
            }

            override fun onClick(view: View) {

                when (view) {
                    next -> {
                        pageViewOperator?.nextPageView()
                    }
                    previous -> {
                        pageViewOperator?.backPageView()
                    }
                }

            }

            override fun onLongClick() {
                hideStoryOverlay()
            }

            override fun onTouchView(view: View, event: MotionEvent): Boolean {
                super.onTouchView(view, event)
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d(TAG, "ACTION_DOWN")
                        pressTime = System.currentTimeMillis()
                        pauseCurrentStory()
                        return false
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d(TAG, "ACTION_UP")
                        resumeCurrentStory()
                        return limit < System.currentTimeMillis() - pressTime
                    }
                }
                return false
            }
        }

        previous.setOnTouchListener(touchListener)
        next.setOnTouchListener(touchListener)

    }

    fun pauseCurrentStory() {
        storiesProgressView?.pause()
    }

    fun resumeCurrentStory() {
        if (onResumeCalled) {
            showStoryOverlay()
            storiesProgressView?.resume()
        }
    }

    private fun showStoryOverlay() {
        if (storyProgressContainer == null || storyProgressContainer.alpha != 0F) return

        storyProgressContainer.animate()
            .setDuration(100)
            .alpha(1F)
            .start()
    }

    fun hideStoryOverlay() {
        if (storyProgressContainer == null || storyProgressContainer.alpha != 1F) return

        storyProgressContainer.animate()
            .setDuration(100)
            .alpha(0F)
            .start()
    }
}
