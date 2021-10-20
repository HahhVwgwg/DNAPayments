package com.dnapayments.presentation.call

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.dnapayments.R
import com.dnapayments.utils.base.BaseHomeBottomFragment

class CallBottomFragment(
    private val taxiPark: String?,
    private val support: String?,
    private val itemClick: (result: Boolean) -> Unit,
) :
    BaseHomeBottomFragment(R.layout.bottom_fragment_call) {

    override fun initViews(view: View?, savedInstanceState: Bundle?) {
        activity?.let {
            view?.run {
                findViewById<TextView>(R.id.taxi_park_phone).text = taxiPark
                findViewById<TextView>(R.id.support_phone).text = support
                findViewById<View>(R.id.support_call).setOnClickListener {
                    itemClick.invoke(false)
                    this@CallBottomFragment.dismiss()
                }
                findViewById<View>(R.id.park_call).setOnClickListener {
                    itemClick.invoke(true)
                    this@CallBottomFragment.dismiss()
                }
                findViewById<View>(R.id.cancel_button).setOnClickListener {
                    this@CallBottomFragment.dismiss()
                }
            }
        }
    }


}