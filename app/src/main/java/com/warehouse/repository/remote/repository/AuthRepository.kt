package com.warehouse.repository.remote.repository

import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.remote.api.AuthApi
import retrofit2.Call

class AuthRepository(private val authApi: AuthApi) {

        fun checkAuth(email: String, password: String): Call<UserDTO> = authApi.checkAuth(email, password)

}