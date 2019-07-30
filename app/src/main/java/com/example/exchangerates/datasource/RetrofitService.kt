package com.example.exchangerates.datasource

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitService {

    private val pbRetrofit = Retrofit.Builder()
        .baseUrl("https://api.privatbank.ua/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val nbuRetrofit = Retrofit.Builder()
        .baseUrl("https://bank.gov.ua/NBUStatService/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val nbuService: ExchangeRatesApi = nbuRetrofit.create(ExchangeRatesApi::class.java)

    val pbService: ExchangeRatesApi = pbRetrofit.create(ExchangeRatesApi::class.java)

}
