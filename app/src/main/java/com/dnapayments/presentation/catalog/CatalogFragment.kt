package com.dnapayments.presentation.catalog

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentCatalogBinding
import com.dnapayments.domain.presentation.Menu
import com.dnapayments.domain.presentation.MenuType
import com.dnapayments.domain.presentation.MenuType.*
import com.dnapayments.utils.base.BaseBindingFragment

class CatalogFragment :
    BaseBindingFragment<FragmentCatalogBinding>(R.layout.fragment_catalog) {

    private val catalogAdapter = CatalogAdapter {
        onMenuClicked(it)
    }


    override fun initViews(savedInstanceState: Bundle?) {
        binding.run {

        }
        initRv()
    }

    private fun initRv() = with(binding) {
        menuRecyclerView.adapter = catalogAdapter
        catalogAdapter.setData(
            listOf(
                Menu("Жыраулар", R.drawable.zhyrau, MODERN_ZHYRAU),
                Menu("Жырлар", R.drawable.aqyndar, MenuType.AQYNDAR),
            )

        )
    }


    private fun onMenuClicked(menu: MenuType) {
        when (menu) {
            AQYNDAR -> {

            }
            MODERN_ZHYRAU -> {

            }
            NEWEST_ZHYRAU -> {

            }
        }
    }
}