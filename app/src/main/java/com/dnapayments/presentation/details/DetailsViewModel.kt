package com.dnapayments.presentation.details

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dnapayments.data.model.Character
import com.dnapayments.data.repository.SearchRepository
import com.dnapayments.utils.base.BaseViewModel

class DetailsViewModel(private val repository: SearchRepository) : BaseViewModel() {
    var characterDetail = ObservableField<Character>()
    val success = MutableLiveData<Boolean>()
}
