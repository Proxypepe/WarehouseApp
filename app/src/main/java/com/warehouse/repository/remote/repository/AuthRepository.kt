package com.warehouse.repository.remote.repository


import com.warehouse.repository.model.UserModel
import com.warehouse.repository.remote.api.AuthApi
import retrofit2.Call

class AuthRepository(private val authApi: AuthApi) {

        fun checkAuth(email: String, password: String): Call<UserModel> = authApi.checkAuth(email, password)

}