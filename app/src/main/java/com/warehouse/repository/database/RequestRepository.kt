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

    fun getUserByEmailNullable(email: String): Flow<UserDTO?> {
        return requestDao.getUserByEmailNullable(email)
    }

    fun getRequestsByUserID(userID: Int): Flow<List<RequestDTO>> {
        return requestDao.getRequestsByUserID(userID)
    }

    fun getUserById(userID: Int): Flow<UserDTO> {
        return requestDao.getUserById(userID)
    }

    fun getRequestById(requestID: Int): Flow<RequestDTO> {
        return requestDao.getRequestById(requestID)
    }

    fun getUsers() = requestDao.getUsers()

    fun getRequests() = requestDao.getRequests()

    @WorkerThread
    suspend fun insertUser(user: UserDTO) {
        requestDao.insertUser(user)
    }

    @WorkerThread
    suspend fun insert(user: UserDTO, requests: List<RequestDTO>) {
        requestDao.insertUserAndRequest(user, requests)
    }

    @WorkerThread
    suspend fun insert(request: RequestDTO) {
        requestDao.insertRequest(request)
    }

    @WorkerThread
    suspend fun insertRequests(user: UserDTO, requests: List<RequestDTO>) {
        requestDao.insertRequests(user, requests)
    }

    @WorkerThread
    suspend fun updateUser(user: UserDTO) {
        requestDao.updateUser(user)
    }

    @WorkerThread
    suspend fun updateRequest(request: RequestDTO) {
        requestDao.updateRequest(request)
    }
}
