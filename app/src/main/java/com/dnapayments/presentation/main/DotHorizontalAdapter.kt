package com.dnapayments.presentation.main

import android.os.Parcelable
import android.view.View
import com.dnapayments.R
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder
import kotlinx.parcelize.Parcelize


class DotHorizontalAdapter : BaseAdapter<Dot>(R.layout.item_dot) {
    fun setSelectedDot(index: Int) {
        items.forEachIndexed { i, dot ->
            dot.selected = i == index
        }
        notifyDataSetChanged()
    }

    override fun bindViewHolder(holder: ViewHolder, data: Dot) {
        holder.itemView.apply {
            findViewById<View>(R.id.dot).setBackgroundResource(if (data.selected) R.drawable.dot_circle_green else R.drawable.dot_circle_grey)
        }
    }
}

@Parcelize
data class Dot(
    var selected: Boolean,
) : Parcelable