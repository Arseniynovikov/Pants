package com.example.pants.di

import org.koin.dsl.module

val dataModule = module {
    includes(networkModule, productRepositoryModule, useCaseModule)
}
