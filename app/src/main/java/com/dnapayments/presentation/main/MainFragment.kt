package com.dnapayments.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.dnapayments.R
import com.dnapayments.databinding.FragmentMainBinding
import com.dnapayments.databinding.SliderItemContainerBinding
import com.dnapayments.presentation.update.UpdateBottomFragment
import com.dnapayments.utils.Constants.NEWS_ITEM
import com.dnapayments.utils.base.BaseBindingFragment
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainFragment :
    BaseBindingFragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {
    private var adapter: DotHorizontalAdapter? = null
    override val vm: MainViewModel by sharedViewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.run {
            viewModel = vm
            adapter = DotHorizontalAdapter()
            dots.adapter = adapter
            refreshLayout.setOnRefreshListener {
                vm.fetchProfile()
            }
            refresh.setOnClickListener {
                vm.fetchProfile()
            }
            vm.success.observe(viewLifecycleOwner, {
                refreshLayout.isRefreshing = !it
            })
            more.setOnClickListener {
                vm.mainBottomSheetSelectedItm.value = 4
            }
            withdraw.setOnClickListener {
                vm.mainBottomSheetSelectedItm.value = 1
            }
            imageCarousel.onScrollListener = object : CarouselOnScrollListener {
                override fun onScrollStateChanged(
                    recyclerView: RecyclerView,
                    newState: Int,
                    position: Int,
                    carouselItem: CarouselItem?,
                ) {
                    // ...
                }

                override fun onScrolled(
                    recyclerView: RecyclerView,
                    dx: Int,
                    dy: Int,
                    position: Int,
                    carouselItem: CarouselItem?,
                ) {
                    adapter?.setSelectedDot(position)
                }
            }
            imageCarousel.carouselListener = object : CarouselListener {
                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup,
                ): ViewBinding {
                    // Here, our XML layout file name is custom_item_layout.xml. So our view binding generated class name is CustomItemLayoutBinding.
                    return SliderItemContainerBinding.inflate(layoutInflater, parent, false)
                }

                override fun onBindViewHolder(
                    binding: ViewBinding,
                    item: CarouselItem,
                    position: Int,
                ) {
                    // Cast the binding to the returned view binding class of the onCreateViewHolder() method.
                    val currentBinding = binding as SliderItemContainerBinding

                    // Do the bindings...
                    currentBinding.imageSlide.apply {
                        // setImage() is an extension function to load image to an ImageView using CarouselItem object. We need to provide current CarouselItem data and the place holder Drawable or drawable resource id to the function. placeholder parameter is optional.
                        setImage(item, R.drawable.carousel_default_placeholder)
                    }

                    currentBinding.imageSlide.setOnClickListener {
                        findNavController().navigate(R.id.action_main_to_news_details,
                            Bundle().apply {
                                putParcelable(NEWS_ITEM, vm.news[position])
                            })
                    }
                }
            }

            imageCarousel.registerLifecycle(viewLifecycleOwner)
            vm.newsList.observe(viewLifecycleOwner, { news ->
                val carouselItems = mutableListOf<CarouselItem>()
                news.forEach {
                    carouselItems.add(CarouselItem(imageUrl = it.image))
                }
                imageCarousel.setData(carouselItems)
                val list = arrayListOf<Dot>()
                for (i in news.indices)
                    list.add(Dot(0 == i))
                adapter?.setData(list)
            })

            vm.showUpdateError.observe(viewLifecycleOwner, {
                UpdateBottomFragment(it) {
                }.show(parentFragmentManager, null)
            })

        }
    }

}