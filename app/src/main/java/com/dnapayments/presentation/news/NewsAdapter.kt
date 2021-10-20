package com.dnapayments.presentation.news

import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dnapayments.R
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class NewsAdapter(private val myCallBack: (result: NotificationElement) -> Unit) :
    BaseAdapter<NotificationElement>(R.layout.item_news) {
    override fun bindViewHolder(holder: ViewHolder, data: NotificationElement) {
        holder.itemView.run {
            findViewById<TextView>(R.id.title).text = data.title
            Glide.with(context).load(data.image)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_document_placeholder)
                    .dontAnimate().error(R.drawable.ic_document_placeholder))
                .into(findViewById(R.id.imageSlide))
            setOnClickListener {
                myCallBack.invoke(data)
            }
        }
    }
}