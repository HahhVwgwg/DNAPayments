package com.dnapayments.di

import com.dnapayments.presentation.activity.LoginViewModel
import com.dnapayments.presentation.activity.MainViewModel
import com.dnapayments.presentation.characters.CharacterViewModel
import com.dnapayments.presentation.details.DetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { MainViewModel() }
    viewModel { DetailsViewModel(get()) }
    viewModel { CharacterViewModel(get()) }
//    viewModel { OrderServiceViewModel() }
//    viewModel { SMSForVerificationViewModel() }
}