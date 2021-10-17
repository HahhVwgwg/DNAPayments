package com.dnapayments.presentation.lesson

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.Lessons
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class LessonViewModel(private val repository: MainRepository) : BaseViewModel() {
    val title = NonNullObservableField("")
    val lessonList = MutableLiveData<List<Lessons>>()
    fun getLessonByCourseId(courseId: Int) {
        viewModelScope.launch {
            isRefreshing.value = true
            val response = repository.fetchLesson(courseId)
            isRefreshing.value = false
            when (response) {
                is SimpleResult.Success -> {
                    lessonList.value = response.data.courses[0].lessons
                }
                is SimpleResult.Error -> {
                    errorString.value = response.errorMessage
                }
                is SimpleResult.NetworkError -> {
                    showNetworkError.value = true
                }
            }
        }
    }
}