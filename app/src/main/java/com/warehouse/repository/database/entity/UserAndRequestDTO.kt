package com.warehouse.repository.database.entity

import androidx.room.Embedded

import androidx.room.Relation

data class UserAndRequestDTO(
    @Embedded val user: UserDTO,
    @Relation(
        parentColumn = "userID",
        entityColumn = "requestID"
    )
    val request: List<RequestDTO>
)
