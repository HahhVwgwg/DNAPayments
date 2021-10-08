package com.dnapayments.presentation.choose_card

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dnapayments.R
import com.dnapayments.data.model.CardOtp
import com.dnapayments.utils.base.BaseHomeBottomFragment

class MultiChoiceBottomFragment(
    private val itemClick: (result: CardOtp) -> Unit,
    private val deleteClick: (result: CardOtp) -> Unit,
    private val openAddCard: (result: Boolean) -> Unit,
    private val lists: List<CardOtp>,
) :
    BaseHomeBottomFragment(R.layout.bottom_fragment_choose_card) {

    private var adapter: MultiChoiceAdapter? = null

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        activity?.let {
            view?.run {
                adapter = MultiChoiceAdapter({
                    this@MultiChoiceBottomFragment.dismiss()
                    itemClick.invoke(it)
                }, {
                    this@MultiChoiceBottomFragment.dismiss()
                    deleteClick.invoke(it)
                })
                findViewById<View>(R.id.cancel_button).setOnClickListener {
                    this@MultiChoiceBottomFragment.dismiss()
                    openAddCard.invoke(false)
                }
                findViewById<View>(R.id.add_card).setOnClickListener {
                    this@MultiChoiceBottomFragment.dismiss()
                    openAddCard.invoke(true)
                }
                adapter?.setData(lists)
                findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
            }
        }
    }


}