package com.warehouse.repository.model

import com.warehouse.repository.database.entity.RequestDTO

data class UserModel (
    val userId: Int,
    val fullname: String,
    val email: String,
    val password: String,
    val role: String,
    val requestDTOS: List<RequestDTO>
)