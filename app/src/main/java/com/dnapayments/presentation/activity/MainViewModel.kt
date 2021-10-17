package com.dnapayments.presentation.activity

import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    val login = NonNullObservableField("")
    val registrationDate = NonNullObservableField("")
    val nameSurname = NonNullObservableField("")
    val title = NonNullObservableField("")
    val status = NonNullObservableField("")
    val motivational = NonNullObservableField("")
    val result = NonNullObservableField(0)
    val total = NonNullObservableField(0)
}