package com.dnapayments.presentation.details

import android.os.Bundle
import com.bumptech.glide.Glide
import com.dnapayments.R
import com.dnapayments.data.model.Zhyrau
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.utils.base.BaseBindingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment :
    BaseBindingFragment<FragmentDetailsBinding>(R.layout.fragment_details) {
    private val vm: DetailsViewModel by viewModel()

    override fun initViews(savedInstanceState: Bundle?) {
        binding.apply {
            back.setOnClickListener {
                onBackPressed()
            }
            requireArguments().getParcelable<Zhyrau>(ZHYRAU)?.let {
                updateUI(it)
            }
            vm.success.observe(viewLifecycleOwner) {
                if (it) hideLoader()
            }
        }
    }

    private fun updateUI(zhyrau: Zhyrau) = with(binding) {
        header.text = zhyrau.name
        Glide.with(requireContext())
            .load(zhyrau.aqynImage)
            .centerCrop()
            .into(image)
        name.text = zhyrau.aqynName
        birthday.text = zhyrau.content
    }

    companion object {
        const val ZHYRAU = "ZHYRAU"
    }
}