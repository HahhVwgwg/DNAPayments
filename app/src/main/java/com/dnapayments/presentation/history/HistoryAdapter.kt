package com.dnapayments.presentation.history

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.dnapayments.R
import com.dnapayments.data.model.WalletTransation
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val myCallBack: (result: WalletTransation) -> Unit) :
    BaseAdapter<WalletTransation>(R.layout.item_history) {
    override fun bindViewHolder(holder: ViewHolder, item: WalletTransation) {
        holder.itemView.run {
            setOnClickListener {
                myCallBack.invoke(item)
            }
            findViewById<TextView>(R.id.card).text = item.transactionDesc
            findViewById<TextView>(R.id.account).text = item.cardNumber
            findViewById<TextView>(R.id.exact_time).text = SimpleDateFormat(
                "HH:mm",
                Locale.getDefault()
            ).format(
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(
                    item.createdAt
                ) ?: Date()
            )
            findViewById<TextView>(R.id.date).text = SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
            ).format(
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(
                    item.createdAt
                ) ?: Date()
            )
            findViewById<TextView>(R.id.sum).text =
                context.getString(R.string.income_sum, item.amount.toString())
            if (item.status == null)
                return
            val status = findViewById<TextView>(R.id.status)
            when (item.status) {
                "pending" -> {
                    status.text = "В ожидании"
                    status.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                "invalid_card" -> {
                    status.text = "Неверная карта"
                    status.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                "processed" -> {
                    status.text = "Завершен"
                    status.setTextColor(ContextCompat.getColor(context, R.color.green))
                }
                "cancelled" -> {
                    status.text = "Отменен"
                    status.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                else -> {
                    status.text = "Ошибка"
                    status.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
            }
        }
    }
}