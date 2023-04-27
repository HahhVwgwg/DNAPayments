package com.dnapayments.di

import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.presentation.details.DetailsViewModel
import com.dnapayments.presentation.profile.SearchViewModel
import com.dnapayments.presentation.stories.StoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //  Forgot password
    viewModel { MainViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { StoryViewModel(get()) }
    viewModel { SearchViewModel(get()) }
//    viewModel { OrderServiceViewModel() }
//    viewModel { SMSForVerificationViewModel() }
}