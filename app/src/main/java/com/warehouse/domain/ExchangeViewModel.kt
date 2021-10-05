package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.warehouse.repository.model.Price
import com.warehouse.repository.remote.api.ExchangeApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ExchangeViewModel(private val exchangeApi: ExchangeApi) : ViewModel(){

    private val compositeDisposable = CompositeDisposable()

    private var price: Price? = null

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun fetchExchange(amount: String,
            from: String, to: String) {
        compositeDisposable.add(exchangeApi.getCulcedExchange(amount, from, to)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ exchangeItem ->
                Log.d("Test", exchangeItem.rates.value.toString())
                exchangeItem?.let {
                    price = Price(exchangeItem.rates.value, to)
                }
            },{

            }))

    }
}

class ExchangeViewModelFactory(private val exchangeApi: ExchangeApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExchangeViewModel(exchangeApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}