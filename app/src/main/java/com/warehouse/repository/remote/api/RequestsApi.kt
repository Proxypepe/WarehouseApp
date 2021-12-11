package com.warehouse.repository.remote.api

import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.RequestModel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RequestsApi {

    @GET("./requests/all")
    fun getAllUsers(): Call<List<RequestDTO>>

    @GET("./requests/{userId}")
    fun getRequestsByUserID(userId: Int): Call<List<RequestDTO>>

    @POST("./requests/add")
    fun addNewRequest(@Body request: RequestModel): Call<RequestDTO>

    @PUT("./requests/update")
    fun updateRequest(requestDTO: RequestDTO): Call<RequestDTO>
}