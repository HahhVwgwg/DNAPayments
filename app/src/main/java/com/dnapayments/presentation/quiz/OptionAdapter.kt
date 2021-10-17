package com.dnapayments.presentation.quiz

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.data.model.Options
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class OptionAdapter(private val myCallBack: (result: Boolean) -> Unit) :
    BaseAdapter<Options>(R.layout.item_question) {
    private var isActive: Boolean = true
    override fun bindViewHolder(holder: ViewHolder, data: Options) {
        holder.itemView.findViewById<TextView>(R.id.option).run {
            text = data.option
            if (!data.selected) {
                findViewById<TextView>(R.id.option).background =
                    ContextCompat.getDrawable(context, R.drawable.grey_border)
            }
            setOnClickListener {
                if (isActive) {
                    isActive = false
                    data.selected = true
                    findViewById<View>(R.id.option).background = ContextCompat.getDrawable(
                        context,
                        if (data.isCorrect == 1) R.drawable.green_border else R.drawable.red_border)
                    myCallBack.invoke(data.isCorrect == 1)
                }
            }
        }
    }

    fun activateOptions() {
        isActive = true
    }
}