package com.warehouse.repository

import android.app.Application
import com.warehouse.repository.database.RequestRepository
import com.warehouse.repository.database.RequestRoomDatabase
import com.warehouse.repository.remote.RetrofitFactory
import com.warehouse.repository.remote.api.*
import com.warehouse.repository.remote.repository.*


class RequestsApplication : Application() {
    private val database by lazy { RequestRoomDatabase.getDatabase(this) }
    val repository by lazy { RequestRepository(database.requestDao()) }

    private val exchangeApi by lazy { RetrofitFactory().getInstance<ExchangeApi>("https://www.frankfurter.app")}
    val remoteRepository by lazy { RemoteRepository(exchangeApi) }

    private val userApi by lazy { RetrofitFactory().getInstance<UserApi>("http://10.0.2.2:8080")}
    val userRepository by lazy { UserRepository(userApi) }

    private val requestApi by lazy { RetrofitFactory().getInstance<RequestsApi>("http://10.0.2.2:8080")}
    val requestsRepository by lazy { RemoteRequestRepository(requestApi) }

    private val authApi by lazy { RetrofitFactory().getInstance<AuthApi>("http://10.0.2.2:8080")}
    val authRepository by lazy { AuthRepository(authApi) }

    private val registerApi by lazy { RetrofitFactory().getInstance<RegisterApi>("http://10.0.2.2:8080")}
    val registerRepository by lazy { RegisterRepository(registerApi) }

}
