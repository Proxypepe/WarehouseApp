package com.warehouse.repository

import android.app.Application
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.RequestRoomDatabase
import com.warehouse.repository.remote.RemoteRepository
import com.warehouse.repository.remote.RetrofitFactory


class RequestsApplication : Application() {
    private val database by lazy { RequestRoomDatabase.getDatabase(this) }
    val repository by lazy { RequestRepository(database.requestDao()) }

    private val exchangeApi by lazy { RetrofitFactory().getExchangeApi()}
    val remoteRepository by lazy { RemoteRepository(exchangeApi) }
}
