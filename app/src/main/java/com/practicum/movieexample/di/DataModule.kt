package com.practicum.movieexample.di

import android.content.Context
import com.google.gson.Gson
import com.practicum.movieexample.data.NetworkClient
import com.practicum.movieexample.data.network.ImdbApi
import com.practicum.movieexample.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ImdbApi> {
        Retrofit.Builder().baseUrl("https://tv-api.com")
            .addConverterFactory(GsonConverterFactory.create()).build().create(ImdbApi::class.java)
    }

    single {
        androidContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }
    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
    factory {
        Gson()
    }

}