package com.dnapayments.presentation.details

import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.base.BaseViewModel

class DetailsViewModel(private val repository: MainRepository) : BaseViewModel() {
    val title = NonNullObservableField("")
}