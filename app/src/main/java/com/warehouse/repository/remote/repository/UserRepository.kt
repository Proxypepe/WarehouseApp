package com.warehouse.repository.remote.repository

import com.warehouse.repository.model.UserModel
import com.warehouse.repository.remote.api.UserApi
import retrofit2.Call

class UserRepository(private val userApi: UserApi) {

    fun getAllUsers(): Call<List<UserModel>> = userApi.getAllUsers()

    fun getUserByEmail(email: String): Call<UserModel> = userApi.getUserByEmail(email)

}