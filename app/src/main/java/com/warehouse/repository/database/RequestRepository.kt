package com.warehouse.repository.database

import androidx.annotation.WorkerThread
import com.warehouse.repository.database.dao.RequestDao
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserAndRequestDTO
import com.warehouse.repository.database.entity.UserDTO
import kotlinx.coroutines.flow.Flow

class RequestRepository (private val requestDao: RequestDao){

    fun getByFullName(fullName: String): Flow<UserDTO> {
        return requestDao.getDataByFullName(fullName)
    }

    fun getUserByEmail(email: String): Flow<UserDTO> {
        return requestDao.getUserByEmail(email)
    }


    @WorkerThread
    suspend fun insertUser(user: UserDTO) {
        requestDao.insertUser(user)
    }

    @WorkerThread
    suspend fun insert(user: UserDTO, requests: List<RequestDTO>) {
        requestDao.insertUserAndRequest(user, requests)
    }
}