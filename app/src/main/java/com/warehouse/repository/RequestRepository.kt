package com.warehouse.repository

import androidx.annotation.WorkerThread
import com.warehouse.repository.database.dao.RequestDao
import com.warehouse.repository.database.entity.RequestDTO
import kotlinx.coroutines.flow.Flow

class RequestRepository (private val requestDao: RequestDao){

    val allRequests: Flow<List<RequestDTO>> = requestDao.getRequests()

    fun getRequestById(id: Int): Flow<RequestDTO>{
        return requestDao.getRequestById(id)
    }

    @WorkerThread
    suspend fun insert(request: RequestDTO) {
        requestDao.insert(request)
    }

    @WorkerThread
    suspend fun deleteAll() {
        requestDao.deleteAll()
    }

    @WorkerThread
    suspend fun update(request: RequestDTO) {
        requestDao.update(request)
    }

    @WorkerThread
    suspend fun delete(request: RequestDTO) {
        requestDao.delete(request)
    }
}