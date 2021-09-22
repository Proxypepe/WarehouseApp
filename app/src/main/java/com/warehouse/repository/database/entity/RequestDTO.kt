package com.warehouse.repository.database.entity


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*

@Entity(tableName = "request")
data class RequestDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @NotNull @NonNull
    @ColumnInfo(name = "product_name")
    val productName: String,
    @NotNull @NonNull
    @ColumnInfo(name = "amount")
    val amount: Int,
    @NotNull @NonNull
    @ColumnInfo(name = "warehouse_place")
    val warehousePlace: Int,
    @NotNull @NonNull
    @ColumnInfo(name = "status")
    val status: String,
    @Nullable
    @ColumnInfo(name = "arrival_date")
    val arrivalDate: Date?
)
