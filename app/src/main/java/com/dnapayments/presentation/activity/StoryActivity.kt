package com.dnapayments.presentation.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.SparseIntArray
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.dnapayments.R
import com.dnapayments.domain.presentation.Story
import com.dnapayments.presentation.stories.PageViewOperator
import com.dnapayments.presentation.stories.StoryDisplayFragment
import kotlinx.android.synthetic.main.activity_story.*
import kz.kmf.mca.ui.custom_views.stories.StoryPagerAdapter


class StoryActivity : AppCompatActivity(), PageViewOperator {

    companion object {
        const val STORIES = "STORIES"
        const val CURRENT_POSITION = "CURRENT_POSITION"
        val progressState = SparseIntArray()
    }

    val resources = intArrayOf(
    )

    private lateinit var pagerAdapter: StoryPagerAdapter
    private var currentPage: Int = 0
    private var storyList: ArrayList<Story> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        storyList = intent.getParcelableArrayListExtra(STORIES)!!
        setupViewPager()
    }

    override fun backPageView() {
        if (story_pager.currentItem > 0) {
            try {
                fakeDrag(false)
            } catch (e: Exception) {
            }
        }
    }

    override fun nextPageView() {
        if (story_pager.currentItem + 1 < story_pager.adapter?.itemCount ?: 0) {
            try {
                fakeDrag(true)
            } catch (e: Exception) {
            }
        } else {
            closeStory()
        }
    }

    override fun closeStory() {
        finish()
        overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_down)
    }

    private fun setupViewPager() {
        pagerAdapter = StoryPagerAdapter(
            this,
            storyList
        )

        story_pager.adapter = pagerAdapter
        story_pager.currentItem = intent.getIntExtra(CURRENT_POSITION, 0)
        story_pager.setPageTransformer(
            CubeOutTransformer()
        )

        story_pager.registerOnPageChangeCallback(object : PageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPage = position
            }

            override fun onPageScrollCanceled() {
                currentFragment().resumeCurrentStory()
            }

        })

    }

    private fun currentFragment(): StoryDisplayFragment {
        return supportFragmentManager.findFragmentByTag("f$currentPage") as StoryDisplayFragment
    }

    private var prevDragPosition = 0

    private fun fakeDrag(forward: Boolean) {
        if (prevDragPosition == 0 && story_pager.beginFakeDrag()) {
            ValueAnimator.ofInt(0, story_pager.width).apply {
                duration = 400L
                interpolator = FastOutSlowInInterpolator()
                addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {}

                    override fun onAnimationEnd(animation: Animator?) {
                        removeAllUpdateListeners()
                        if (story_pager.isFakeDragging) {
                            story_pager.endFakeDrag()
                        }
                        prevDragPosition = 0
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        removeAllUpdateListeners()
                        if (story_pager.isFakeDragging) {
                            story_pager.endFakeDrag()
                        }
                        prevDragPosition = 0
                    }

                    override fun onAnimationStart(p0: Animator?) {}
                })
                addUpdateListener {
                    if (!story_pager.isFakeDragging) return@addUpdateListener
                    val dragPosition: Int = it.animatedValue as Int
                    val dragOffset: Float =
                        ((dragPosition - prevDragPosition) * if (forward) -1 else 1).toFloat()
                    prevDragPosition = dragPosition
                    story_pager.fakeDragBy(dragOffset)
                }
            }.start()
        }
    }

}

