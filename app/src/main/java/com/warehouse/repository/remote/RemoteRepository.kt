package com.warehouse.repository.remote

import com.warehouse.repository.model.ExchangeItem
import com.warehouse.repository.model.Price
import com.warehouse.repository.remote.api.ExchangeApi
import retrofit2.Call

class RemoteRepository(private val exchangeApi: ExchangeApi) {

    fun getExchangeItem(amount: String, from: String, to: String ) : Call<ExchangeItem> {
        return exchangeApi.getCulcedExchange(amount, from, to)
    }

}