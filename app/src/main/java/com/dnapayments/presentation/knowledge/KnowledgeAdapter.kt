package com.dnapayments.presentation.knowledge

import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.data.model.NotificationElement
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class KnowledgeAdapter(private val myCallBack: (result: NotificationElement) -> Unit) :
    BaseAdapter<NotificationElement>(R.layout.item_knowledge) {
    override fun bindViewHolder(holder: ViewHolder, data: NotificationElement) {
        holder.itemView.run {
            setOnClickListener {
                myCallBack.invoke(data)
            }
            findViewById<TextView>(R.id.knowledge).text = data.title
        }
    }
}