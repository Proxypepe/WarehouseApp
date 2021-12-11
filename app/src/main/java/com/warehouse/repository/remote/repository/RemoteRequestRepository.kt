package com.warehouse.repository.remote.repository

import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.RequestModel
import com.warehouse.repository.remote.api.RequestsApi
import retrofit2.Call

class RemoteRequestRepository(private val requestsApi: RequestsApi) {

    fun getAllUsers() = requestsApi.getAllUsers()
    fun getRequestsByUserID(userID: Int) = requestsApi.getRequestsByUserID(userID)
    fun addNewRequest(request: RequestModel) = requestsApi.addNewRequest(request)
    fun updateRequest(request: RequestDTO): Call<RequestDTO> = requestsApi.updateRequest(request)

}