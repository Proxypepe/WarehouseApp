package com.warehouse.repository.remote

import com.warehouse.repository.remote.api.ExchangeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private lateinit var exchangeApi: ExchangeApi

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.frankfurter.app")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        exchangeApi = retrofit.create(ExchangeApi::class.java)
    }

    fun getExchangeApi(): ExchangeApi {
        configureRetrofit()
        return exchangeApi
    }
}