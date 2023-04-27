package com.dnapayments.presentation.catalog

import android.widget.ImageView
import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.domain.presentation.Menu
import com.dnapayments.domain.presentation.MenuType
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class CatalogAdapter(private val onClick: (MenuType) -> Unit) :
    BaseAdapter<Menu>(R.layout.item_catalog) {

    override fun bindViewHolder(holder: ViewHolder, data: Menu) {
        val titleTextView: TextView = holder.itemView.findViewById(R.id.titleTextView)
        val imageView: ImageView = holder.itemView.findViewById(R.id.imageView)

        titleTextView.text = data.title
        imageView.setImageResource(data.imageResourceId)

        holder.itemView.setOnClickListener {
            onClick.invoke(data.menuType)
        }
    }
}