package com.warehouse.repository.remote.api

import com.warehouse.repository.model.ExchangeItem
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ExchangeApi {

    @GET("./latest")
    @Headers("Content-Type: application/json;")
    fun getCulcedExchange
                (@Query("amount") amount: String, @Query("from") from: String, @Query("to") to: String) : Call<ExchangeItem>

}
