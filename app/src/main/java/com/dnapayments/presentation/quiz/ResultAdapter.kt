package com.dnapayments.presentation.quiz

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.data.model.Progress
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class ResultAdapter : BaseAdapter<Progress>(R.layout.item_result) {
    override fun bindViewHolder(holder: ViewHolder, data: Progress) {
        holder.itemView.run {
            findViewById<TextView>(R.id.position).text = data.position.toString()
            when {
                data.current -> {
                    findViewById<TextView>(R.id.position).setTextColor(ContextCompat.getColor(
                        context,
                        R.color.white))
                    findViewById<View>(R.id.outside).background.setTint(ContextCompat.getColor(
                        context,
                        R.color.bg_color))
                    findViewById<View>(R.id.inside).background.setTint(ContextCompat.getColor(
                        context,
                        R.color.bg_color))
                }
                data.notAnswered -> {
                    findViewById<TextView>(R.id.position).setTextColor(ContextCompat.getColor(
                        context,
                        R.color.grey))
                    findViewById<View>(R.id.outside).background.setTint(ContextCompat.getColor(
                        context,
                        R.color.grey))
                    findViewById<View>(R.id.inside).background.setTint(ContextCompat.getColor(
                        context,
                        R.color.white))
                }
                else -> {
                    findViewById<View>(R.id.inside).background.setTint(ContextCompat.getColor(
                        context,
                        R.color.white))
                    findViewById<TextView>(R.id.position).setTextColor(ContextCompat.getColor(
                        context,
                        if (data.answerWasTrue) R.color.green_light else R.color.red))
                    findViewById<View>(R.id.outside).background.setTint(ContextCompat.getColor(
                        context,
                        if (data.answerWasTrue) R.color.green_light else R.color.red))
                }
            }
        }
    }
}