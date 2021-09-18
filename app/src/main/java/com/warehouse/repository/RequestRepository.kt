package com.warehouse.repository

import androidx.annotation.WorkerThread
import com.warehouse.repository.database.dao.RequestDao
import com.warehouse.repository.database.entity.Request
import kotlinx.coroutines.flow.Flow

class RequestRepository (private val requestDao: RequestDao){

    val allRequests: Flow<List<Request>> = requestDao.getRequests()

    @WorkerThread
    suspend fun insert(request: Request) { 
        requestDao.insert(request)
    }

    @WorkerThread
    suspend fun deleteAll() {
        requestDao.deleteAll()
    }

    @WorkerThread
    suspend fun update(request: Request) {
        requestDao.update(request)
    }

}