package com.dnapayments.presentation.details

import android.os.Bundle
import com.dnapayments.R
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding, DetailsViewModel>(R.layout.fragment_details) {
    override val vm: DetailsViewModel by viewModel()
    override fun initViews(savedInstanceState: Bundle?) {
        binding?.apply {
            viewModel = vm
            back.setOnClickListener {
                onBackPressed()
            }
            arguments?.getInt("char_id")?.let {
                showLoader()
                vm.fetchCharacterById(it)
            }
            vm.success.observe(viewLifecycleOwner, {
                if (it) hideLoader()
            })
        }
    }
}