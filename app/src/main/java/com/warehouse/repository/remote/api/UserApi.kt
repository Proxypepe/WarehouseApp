package com.warehouse.repository.remote.api

import com.warehouse.repository.model.UserModel
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path

// Base url: http://10.0.2.2:8080/

interface UserApi {
    @GET("./user/all")
    fun getAllUsers(): Call<List<UserModel>>

    @GET("user/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<UserModel>

}