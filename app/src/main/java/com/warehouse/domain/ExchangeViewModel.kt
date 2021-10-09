package com.warehouse.domain

import android.util.Log
import androidx.lifecycle.*
import com.warehouse.repository.model.ExchangeItem
import com.warehouse.repository.model.Price
import com.warehouse.repository.remote.RemoteRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeViewModel(private val remoteRepository: RemoteRepository) : ViewModel(){

    // private val compositeDisposable = CompositeDisposable()
    private val _price = MutableLiveData(Price(-1.0, "RUB"))
    var price: LiveData<Price> = _price

    val loading = MutableLiveData(false)

//    override fun onCleared() {
//        compositeDisposable.clear()
//        super.onCleared()
//    }



    fun getPrice(amount: String, from: String, to: String){
        loading.value = true
        remoteRepository.getExchangeItem(amount, from, to).enqueue(object : Callback<ExchangeItem> {
            override fun onResponse(call: Call<ExchangeItem>, response: Response<ExchangeItem>) {

                if (response.isSuccessful) {
                    val req = response.body()
                    req?.let{
                        price = MutableLiveData(Price(req.rates.value , to))
                        loading.value = false

                    }
                }

            }

            override fun onFailure(call: Call<ExchangeItem>, t: Throwable) {
                loading.value = false
            }
        })


    }


    
//    fun getPrice(amount: String, from: String, to: String) : MutableState<Price> {
//        fetchExchange(amount, from, to)
//        return price
//    }
}

class ExchangeViewModelFactory(private val remoteRepository: RemoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExchangeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExchangeViewModel(remoteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//    fun fetchExchange(amount: String, from: String, to: String) {
//
//        loading.value = true
//        compositeDisposable.add(remoteRepository.getExchangeItem(amount, from, to)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ exchangeItem ->
//                Log.d("Test", exchangeItem.rates.value.toString())
//                exchangeItem?.let {
//                    price.value = Price(exchangeItem.rates.value, to)
//                }
//                loading.value = false
//            }, {
//                loading.value = false
//            })
//        )
//
//    }



//@WorkerThread
//fun getPrice(amount: String, from: String, to: String) {
//    viewModelScope.launch {
//        loading.value = true
//        delay(2000)
//        val response =  remoteRepository.getExchangeItem(amount, from, to)
//        price.value = Price(response?.rates?.value ?: -0.0, response?.base ?: "RUB")
//
//        loading.value = false
//    }
//}