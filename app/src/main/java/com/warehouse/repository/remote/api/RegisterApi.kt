package com.warehouse.repository.remote.api

import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi{

    @POST("./register")
    fun createUser(@Body user: User): Call<UserDTO>

}