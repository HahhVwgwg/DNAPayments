package com.dnapayments.presentation.profile

import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.domain.presentation.SearchResult
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class SearchAdapter(private val onClick: (SearchResult) -> Unit) :
    BaseAdapter<SearchResult>(R.layout.item_search) {

    override fun bindViewHolder(holder: ViewHolder, data: SearchResult) {
        val headerTextView: TextView = holder.itemView.findViewById(R.id.headerTextView)
        val subheaderTextView: TextView = holder.itemView.findViewById(R.id.subheaderTextView)

        headerTextView.text = data.header
        subheaderTextView.text = data.subHeader

        holder.itemView.setOnClickListener {
            onClick.invoke(data)
        }
    }
}