package com.dnapayments.presentation.registration

import android.view.View
import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.data.model.ParkElement
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class ChooseParkAdapter : BaseAdapter<ParkElement>(R.layout.item_choose_park) {
    override fun bindViewHolder(holder: ViewHolder, data: ParkElement) {
        holder.itemView.run {
            findViewById<TextView>(R.id.park_name).text = data.parkName
            findViewById<View>(R.id.checkbox).visibility =
                if (data.selected) View.VISIBLE else View.INVISIBLE
            setOnClickListener {
                selectData(data)
            }
        }
    }

    private fun selectData(data: ParkElement) {
        this.items.forEach {
            it.selected = it == data
        }
        notifyDataSetChanged()
    }

    fun getSelected(): Long {
        this.items.forEach {
            if (it.selected) return it.fleet
        }
        return 0
    }
}