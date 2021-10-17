package com.dnapayments.presentation.characters

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.Courses
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class CourseViewModel(private val repository: MainRepository) : BaseViewModel() {
    var coursesList = MutableLiveData<List<Courses>>()
    var listSize = ObservableField(0)

    init {
        fetchCourses()
    }

    fun fetchCourses() {
        viewModelScope.launch {
            isRefreshing.value = true
            val response = repository.fetchCourseList()
            isRefreshing.value = false
            when (response) {
                is SimpleResult.Success -> {
                    response.data.let {
                        coursesList.value = it.courses
                        listSize.set(it.courses.size)
                    }
                }
                is SimpleResult.Error -> {
                    response.errorMessage.let {
                        errorString.value = it
                    }
                }
                is SimpleResult.NetworkError -> {
                }
            }
        }
    }
}
