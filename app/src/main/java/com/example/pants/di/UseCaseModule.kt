package com.example.pants.di

import com.example.pants.domain.usecase.CheckBoardOrderUseCase
import com.example.pants.domain.usecase.GetColorBoardUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetColorBoardUseCase)
    factoryOf(::CheckBoardOrderUseCase)
}
