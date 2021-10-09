package com.warehouse.repository.model

import com.google.gson.annotations.SerializedName


data class ExchangeItem(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: Rates,
)

data class Rates(
    @SerializedName(value = "RUB", alternate = ["USD", "GBP"])
    val value: Double
)