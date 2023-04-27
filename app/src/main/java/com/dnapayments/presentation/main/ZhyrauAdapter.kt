package com.dnapayments.presentation.main

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dnapayments.R
import com.dnapayments.data.model.Zhyrau
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class ZhyrauAdapter(private val onClick: (Zhyrau) -> Unit) :
    BaseAdapter<Zhyrau>(R.layout.item_zhyrau) {

    override fun bindViewHolder(holder: ViewHolder, data: Zhyrau) {
        val headerTextView: TextView = holder.itemView.findViewById(R.id.headerTextView)
        val subheaderTextView: TextView = holder.itemView.findViewById(R.id.subheaderTextView)
        val imageView: ImageView = holder.itemView.findViewById(R.id.imageView)

        headerTextView.text = data.name
        subheaderTextView.text = data.aqynName

        Glide.with(holder.itemView.context)
            .load(data.aqynImage)
            .centerCrop()
            .into(imageView)

        holder.itemView.setOnClickListener {
            onClick.invoke(data)
        }
    }
}