package com.dnapayments.presentation.activity

import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.*

abstract class PageChangeCallback : ViewPager2.OnPageChangeCallback() {

  companion object {
    private const val DEBOUNCE_TIMES = 500L
  }

  private var currentPage = 0
  private var pageBeforeDragging = 0
  private var lastTime = DEBOUNCE_TIMES + 1L

  override fun onPageScrollStateChanged(state: Int) {
    super.onPageScrollStateChanged(state)
    when (state) {
      SCROLL_STATE_IDLE -> {
        val now = System.currentTimeMillis()
        if (now - lastTime < DEBOUNCE_TIMES) {
          return
        }
        lastTime = now
        if (pageBeforeDragging == currentPage) {
          onPageScrollCanceled()
        }
      }
      SCROLL_STATE_DRAGGING -> {
        pageBeforeDragging = currentPage
      }
      SCROLL_STATE_SETTLING -> {
      }
    }
  }

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
  }

  override fun onPageSelected(position: Int) {
    currentPage = position
  }

  abstract fun onPageScrollCanceled()

}
