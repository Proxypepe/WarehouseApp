package com.warehouse.repository.remote.api

import com.warehouse.repository.database.entity.UserDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthApi {
    @GET("./auth")
    fun checkAuth(@Query("email") email: String, @Query("password") password: String): Call<UserDTO>

}