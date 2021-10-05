package com.warehouse.repository.database.entity


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.warehouse.repository.model.Contact
import com.warehouse.repository.model.Price
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
    val arrivalDate: Date?,
    @Nullable
    @Embedded val contact: Contact?,
    @Nullable
    @Embedded val price: Price?
)

// https://developer.android.com/training/data-storage/room/relationships