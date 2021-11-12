package com.dnapayments.presentation.characters

import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dnapayments.R
import com.dnapayments.data.model.Character
import com.dnapayments.utils.base.BaseAdapter
import com.dnapayments.utils.base.ViewHolder

class CharacterAdapter(private val myCallBack: (result: Int) -> Unit) :
    BaseAdapter<Character>(R.layout.item_character) {
    override fun bindViewHolder(holder: ViewHolder, data: Character) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.name).text = data.name
            findViewById<TextView>(R.id.birthday).text = data.birthday
            findViewById<TextView>(R.id.status).text = data.status
            Glide.with(holder.itemView.context)
                .load(data.img)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_document_placeholder)
                    .dontAnimate().error(R.drawable.ic_document_placeholder))
                .into(findViewById(R.id.image))
            setOnClickListener {
                myCallBack.invoke(data.char_id)
            }
        }
    }
}