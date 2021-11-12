package com.dnapayments.di

import com.dnapayments.data.repository.CharacterDetailedRepository
import com.dnapayments.data.repository.CharacterRepository
import com.dnapayments.data.repository.LoginRepository
import com.dnapayments.data.repository.MainRepository
import org.koin.dsl.module

val repoModule = module {
    single { MainRepository(get()) }
    single { LoginRepository(get()) }
    single { CharacterDetailedRepository(get()) }
    single { CharacterRepository(get()) }
}
