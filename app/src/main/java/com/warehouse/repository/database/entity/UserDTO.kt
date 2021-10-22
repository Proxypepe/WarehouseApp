package com.warehouse.repository.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "User")
data class UserDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    @NotNull @NonNull
    val userID: Int = 1,
    @ColumnInfo(name = "fullname")
    @NotNull @NonNull
    val fullname: String,
    @ColumnInfo(name = "email")
    @NotNull @NonNull
    val email: String,
    @ColumnInfo(name = "password")
    @NotNull @NonNull
    val password: String,
    @ColumnInfo(name = "role")
    @NotNull @NonNull
    val role: String,
)
