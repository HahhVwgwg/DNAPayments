package com.dnapayments.presentation.quiz

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dnapayments.data.model.Options
import com.dnapayments.data.model.Progress
import com.dnapayments.data.model.Quizzes
import com.dnapayments.data.model.SimpleResult
import com.dnapayments.data.repository.MainRepository
import com.dnapayments.utils.NonNullObservableField
import com.dnapayments.utils.PrefsAuth
import com.dnapayments.utils.SingleLiveData
import com.dnapayments.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class QuizViewModel(private val repository: MainRepository, private val prefsAuth: PrefsAuth) :
    BaseViewModel() {
    val title = NonNullObservableField("")
    val totalSize = NonNullObservableField(0)
    val current = NonNullObservableField(0)
    val selectedQuizTitle = NonNullObservableField("")
    val showLoader = NonNullObservableField(false)
    var quizzes = arrayListOf<Quizzes>()
    var progressList = arrayListOf<Progress>()
    var currentIndex = 0
    val options = MutableLiveData<List<Options>>()
    val progress = MutableLiveData<List<Progress>>()
    var totalAnsweredOptions = 0
    val endOfQuiz = SingleLiveData<Boolean>()
    private var localLessonId: Int = 0
    var passed = false

    fun fetchQuizzesById(lessonId: Int) {
        localLessonId = lessonId
        viewModelScope.launch {
            showLoader.set(true)
            val response = repository.fetchQuizzes(lessonId)
            showLoader.set(false)
            when (response) {
                is SimpleResult.Success -> {
                    quizzes = response.data.quizes as ArrayList<Quizzes>
                    initialize()
                }
                is SimpleResult.Error -> {
                    errorString.value = response.errorMessage
                }
                is SimpleResult.NetworkError -> {
                    showNetworkError
                }
            }
        }
    }

    private fun sendExamResults() {
        viewModelScope.launch {
            showLoader.set(true)
            val response =
                repository.saveQuizResult(localLessonId,
                    if (totalAnsweredOptions == totalSize.get()) 1 else 0,
                    prefsAuth.getUser().id,
                    totalAnsweredOptions)
            showLoader.set(false)
            when (response) {
                is SimpleResult.Success -> {
                    endOfQuiz.value = true
                }
                is SimpleResult.Error -> {
                    errorString.value = response.errorMessage
                }
                is SimpleResult.NetworkError -> {
                    showNetworkError
                }
            }
        }
    }

    private fun initialize() {
        options.value = quizzes[0].options
        selectedQuizTitle.set(quizzes[0].question)
        current.set(1)
        for (i in 0 until quizzes.size) {
            progressList.add(Progress(current = 0 == i,
                position = i + 1,
                answerWasTrue = false,
                notAnswered = true))
        }
        totalSize.set(quizzes.size)
        progress.value = progressList
    }

    fun showNextOption(isAnswerWasTrue: Boolean) {
        if (isAnswerWasTrue) {
            totalAnsweredOptions++
        }
        if (currentIndex == quizzes.size - 1) {
            if (passed) {
                endOfQuiz.value = true
            } else {
                sendExamResults()
            }
            return
        }
        current.set(current.get() + 1)
        progressList[currentIndex].answerWasTrue = isAnswerWasTrue
        progressList[currentIndex].notAnswered = false
        progressList[currentIndex].current = false
        currentIndex++
        progressList[currentIndex].current = true
        options.value = quizzes[currentIndex].options
        selectedQuizTitle.set(quizzes[currentIndex].question)
        progress.value = progressList
    }
}