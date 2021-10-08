package com.dnapayments.presentation.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.dnapayments.R
import com.dnapayments.data.model.SliderItem
import com.dnapayments.databinding.FragmentMainBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment :
    BaseBindingFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {

    private lateinit var viewPager2: ViewPager2
    override val vm: MainViewModel by sharedViewModel()
    var sliderHandler = Handler(Looper.getMainLooper())
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.run {
            viewModel = vm
            viewPager2 = onBoardingViewPager
            val sliderItems = mutableListOf(
                SliderItem(R.drawable.banner3),
                SliderItem(R.drawable.bannernew)
            ) as ArrayList
            viewPager2.adapter =
                SliderAdapter(sliderItems, viewPager2
                ) { action ->
                    if (action == MotionEvent.ACTION_DOWN) {
                        freeze = true
                    } else if (action == MotionEvent.ACTION_UP) {
                        freeze = false
                        sliderHandler.removeCallbacks(sliderRunnable)
                        sliderHandler.postDelayed(sliderRunnable, 3000)
                    }
                }
            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.offscreenPageLimit = 2
            viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(50))
            viewPager2.setPageTransformer(compositePageTransformer)
            viewPager2.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() { // релиз откоммент
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 3000)
                }
            })

            refreshLayout.setOnRefreshListener {
                vm.fetchProfile()
            }
            refresh.setOnClickListener {
                vm.fetchProfile()
            }
            vm.success.observe(viewLifecycleOwner, {
                refreshLayout.isRefreshing = !it
            })
        }
    }


    private var freeze = false


    var sliderRunnable = Runnable {
        if (!freeze) {
            viewPager2.currentItem = viewPager2.currentItem + 1 // откоммент релиз
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable) //откоммент релиз
    }

}