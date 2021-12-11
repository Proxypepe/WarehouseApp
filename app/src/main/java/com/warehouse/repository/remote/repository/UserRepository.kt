package com.warehouse.repository.remote.repository

import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.remote.api.UserApi
import retrofit2.Call

class UserRepository(private val userApi: UserApi) {

    fun getAllUsers(): Call<List<UserDTO>> = userApi.getAllUsers()

}