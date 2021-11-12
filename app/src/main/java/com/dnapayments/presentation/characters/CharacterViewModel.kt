package com.dnapayments.presentation.characters

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.Resource
import com.dnapayments.data.model.Character
import com.dnapayments.data.repository.CharacterRepository
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : BaseViewModel() {
    var characterList = MutableLiveData<List<Character>>()
    var listSize = ObservableField(0)

    init {
        fetchCharacter()
    }

    fun fetchCharacter() {
        viewModelScope.launch {
            isRefreshing.value = true
            val response = repository.fetchCharacters()
            isRefreshing.value = false
            when (response.status) {
                Resource.Status.SUCCESS -> {
                    response.data?.let {
                        characterList.value = it
                        listSize.set(characterList.value?.size)
                    }
                }
                Resource.Status.ERROR -> {
                    response.errorMessage.let {
                        errorString.value = it
                    }
                }
                else -> {
                }
            }
        }
    }
}
