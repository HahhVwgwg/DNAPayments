package com.dnapayments.presentation.characters

import android.annotation.SuppressLint
import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.data.model.Courses
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class CourseMainAdapter(private val myCallBack: (result: Courses) -> Unit) :
    BaseAdapter<Courses>(R.layout.item_character) {
    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Courses) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.header).text = data.title
            findViewById<TextView>(R.id.sub_header).text = "${data.lessonsCount} сабақ"
            setOnClickListener {
                myCallBack.invoke(data)
            }
        }
    }
}