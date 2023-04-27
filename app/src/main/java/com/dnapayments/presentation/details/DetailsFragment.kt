package com.dnapayments.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import com.bumptech.glide.Glide
import com.dnapayments.R
import com.dnapayments.data.model.Zhyrau
import com.dnapayments.databinding.FragmentDetailsBinding
import com.dnapayments.utils.base.BaseBindingFragment
import com.dnapayments.utils.fromHtml
import com.dnapayments.utils.makeLinks
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


    @SuppressLint("ClickableViewAccessibility")
    private fun updateUI(zhyrau: Zhyrau) = with(binding) {
        header.text = zhyrau.name
        Glide.with(requireContext())
            .load(zhyrau.aqynImage)
            .centerCrop()
            .into(image)
        name.text = zhyrau.aqynName
        content.text = zhyrau.content?.replace("\\n", "\n")

        content.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && content.isTextSelectable) {
                val start = content.selectionStart
                val end = content.selectionEnd
                if (start != end) {
                    val list = hashSetOf(
                        "сабадан",
                        "Кіреуке",
                        "Күдеріден",
                        "Күренді",
                        "сайгез оқ",
                        "бұлан"
                    )
                    // Text is selected
                    val selectedText = content.text.toString().substring(start, end)
                    content.clearFocus()
                    if (!list.contains(selectedText))
                        MultiChoiceBottomFragment(selectedText).show(parentFragmentManager, null)
                }
            }
            false
        }
        content.makeLinks(
            Pair("сабадан", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/IMG_2058.JPG?alt=media&token=3e1e4f46-e75d-438a-b65e-71a33dbb4b7f")
            }),
            Pair("Кіреуке", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/Image%2FIMG_2055.JPG?alt=media&token=97d72ce1-dbe0-42a7-bb16-3da0d64b1ee1")
            }),
            Pair("Күдеріден", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/Image%2FIMG_2053.JPG?alt=media&token=778e7391-6d43-4592-9e68-fac0f43cf0f7")
            }),
            Pair("Күренді", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/Image%2FIMG_2057.JPG?alt=media&token=c8483a63-7c46-4f17-800e-04546221e377")
            }),
            Pair("сайгез оқ", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/Image%2FIMG_2056.JPG?alt=media&token=d7b3b8fa-a683-4dcf-b0c0-5c69275013c0")
            }),
            Pair("бұлан", View.OnClickListener {
                openLink("https://firebasestorage.googleapis.com/v0/b/diploma-c971d.appspot.com/o/Image%2FIMG_2059.JPG?alt=media&token=9a4525f3-3720-406a-90fa-9c72b05ce437")
            })
        )
    }

    private fun openLink(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    companion object {
        const val ZHYRAU = "ZHYRAU"
    }
}