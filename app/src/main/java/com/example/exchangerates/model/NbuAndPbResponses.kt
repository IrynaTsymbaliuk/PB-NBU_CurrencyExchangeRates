package com.example.exchangerates.model

import com.google.gson.annotations.SerializedName

data class NbuRate (
    @SerializedName("txt")
    val currency: String,
    @SerializedName("rate")
    val saleRate: Float,
    @SerializedName("cc")
    val currencyCodeL: String
)

data class PbRatesResponse(

    @SerializedName("dateOfRates")
    val date: String,
    @SerializedName("bank")
    val bank: String,
    @SerializedName("baseCurrency")
    val baseCurrency: Int,
    @SerializedName("baseCurrencyLit")
    val baseCurrencyLit: String,
    @SerializedName("exchangeRate")
    val listOfPbRates: List<PbRate>

)

data class PbRate(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("saleRate")
    val saleRate: Double,
    @SerializedName("purchaseRate")
    val purchaseRate: Double
)