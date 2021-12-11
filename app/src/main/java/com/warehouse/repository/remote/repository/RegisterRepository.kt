package com.warehouse.repository.remote.repository

import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.User
import com.warehouse.repository.remote.api.RegisterApi
import retrofit2.Call

class RegisterRepository(private val registerApi: RegisterApi) {
    fun createUser(user: User): Call<UserDTO> = registerApi.createUser(user)
}

