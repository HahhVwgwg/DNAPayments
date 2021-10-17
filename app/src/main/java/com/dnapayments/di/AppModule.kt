package com.dnapayments.di

import com.dnapayments.presentation.activity.LoginViewModel
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.presentation.characters.CourseViewModel
import com.dnapayments.presentation.details.DetailsViewModel
import com.dnapayments.presentation.lesson.LessonViewModel
import com.dnapayments.presentation.quiz.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //  Forgot password
    viewModel { CourseViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { LessonViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { QuizViewModel(get(), get()) }
//    viewModel { OrderServiceViewModel() }
//    viewModel { SMSForVerificationViewModel() }
}