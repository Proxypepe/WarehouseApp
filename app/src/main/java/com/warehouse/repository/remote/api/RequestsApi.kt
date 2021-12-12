package com.warehouse.repository.remote.api

import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.RequestModel
import com.warehouse.repository.model.RequestResponseModel

import retrofit2.Call
import retrofit2.http.*

interface RequestsApi {

    @GET("requests/all")
    fun getAllUsers(): Call<List<RequestDTO>>

    @GET("requests/{userId}")
    fun getRequestsByUserID(@Path("userId")userId: Int): Call<List<RequestDTO>>

    @POST("requests/add")
    fun addNewRequest(@Body request: RequestModel): Call<RequestResponseModel>

    @PUT("requests/update")
    fun updateRequest(@Body requestDTO: RequestDTO): Call<RequestDTO>
}