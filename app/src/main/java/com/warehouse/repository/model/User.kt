package com.warehouse.repository.model

data class User(
    val fullname: String,
    val email: String,
    val password: String,
    val role: String
)