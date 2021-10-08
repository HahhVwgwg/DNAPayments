package com.dnapayments.di

import com.dnapayments.presentation.add_card.AddCardViewModel
import com.dnapayments.presentation.characters.CharacterViewModel
import com.dnapayments.presentation.details.DetailsViewModel
import com.dnapayments.presentation.history.HistoryViewModel
import com.dnapayments.presentation.history_detail.HistoryDetailViewModel
import com.dnapayments.presentation.main.MainViewModel
import com.dnapayments.presentation.registration.AuthorizationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //  Forgot password
    viewModel { CharacterViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { AuthorizationViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
    viewModel { HistoryDetailViewModel(get()) }
    viewModel { AddCardViewModel(get()) }
//    viewModel { OrderServiceViewModel() }
//    viewModel { SMSForVerificationViewModel() }
}