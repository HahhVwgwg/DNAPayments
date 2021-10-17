package com.dnapayments.presentation.lesson

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.data.model.Lessons
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class LessonAdapter(
    private val myCallBack: (result: Lessons) -> Unit,
    private val myCallBack2: () -> Unit,
) :
    BaseAdapter<Lessons>(R.layout.item_lesson) {

    @SuppressLint("SetTextI18n")
    override fun bindViewHolder(holder: ViewHolder, data: Lessons) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.title).text = data.title
            setOnClickListener {
                if (data.position == 0 || items[data.position - 1].result?.passed == 1) myCallBack.invoke(
                    data)
                else
                    myCallBack2.invoke()
            }
            when {
                data.position == 0 || items[data.position - 1].result?.passed == 1 -> {
                    findViewById<TextView>(R.id.title).setTextColor(ContextCompat.getColor(context,
                        R.color.contentTextColor))
                    findViewById<TextView>(R.id.title).setCompoundDrawablesWithIntrinsicBounds(0,
                        0,
                        R.drawable.ic_arrow_right,
                        0)
                }
                else -> {
                    findViewById<TextView>(R.id.title).setTextColor(ContextCompat.getColor(context,
                        R.color.grey))
                    findViewById<TextView>(R.id.title).setCompoundDrawablesWithIntrinsicBounds(0,
                        0,
                        R.drawable.ic_lock,
                        0)
                }
            }
        }
    }

}