package com.warehouse.repository.database

import androidx.annotation.WorkerThread
import com.warehouse.repository.database.dao.RequestDao
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserAndRequestDTO
import kotlinx.coroutines.flow.Flow

class RequestRepository (private val requestDao: RequestDao){

    fun getByFullName(fullName: String): Flow<UserAndRequestDTO> {
        return requestDao.getDataByFullName(fullName)
    }

//    @WorkerThread
//    suspend fun insert(userAndRequestDTO: UserAndRequestDTO) {
//        requestDao.insert(userAndRequestDTO)
//    }



//    fun getRequestById(id: Int): Flow<RequestDTO>{
//        return requestDao.getRequestById(id)
//    }
//    @WorkerThread
//    suspend fun deleteAll() {
//        requestDao.deleteAll()
//    }
//
//    @WorkerThread
//    suspend fun update(request: RequestDTO) {
//        requestDao.update(request)
//    }
//
//    @WorkerThread
//    suspend fun delete(request: RequestDTO) {
//        requestDao.delete(request)
//    }
}