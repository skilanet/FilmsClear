package com.practicum.movieexample.di

import com.practicum.movieexample.data.MoviesRepositoryImpl
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.api.MoviesRepository
import com.practicum.movieexample.domain.impl.MoviesInteractorImpl
import org.koin.dsl.module

val domainModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(get())
    }
    single<MoviesInteractor> {
        MoviesInteractorImpl(get())
    }
}