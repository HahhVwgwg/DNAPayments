package com.dnapayments.presentation.activity

import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel

class MainActivityViewModel : BaseViewModel() {
    val viewState = NonNullObservableField("true")
}