package com.dnapayments.presentation.choose_card

import android.view.View
import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.data.model.CardOtp
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class MultiChoiceAdapter(
    private val itemClick: (result: CardOtp) -> Unit,
    private val deleteClick: (result: CardOtp) -> Unit,
) :
    BaseAdapter<CardOtp>(R.layout.item_card) {
    override fun bindViewHolder(holder: ViewHolder, data: CardOtp) {
        holder.itemView.run {
            findViewById<TextView>(R.id.card_item_name).text = data.cardName
            findViewById<TextView>(R.id.card_item_number).text = data.lastFour
            findViewById<View>(R.id.deleteCardBtn).setOnClickListener {
                deleteClick.invoke(data)
            }
            setOnClickListener {
                itemClick.invoke(data)
            }
        }
    }
}