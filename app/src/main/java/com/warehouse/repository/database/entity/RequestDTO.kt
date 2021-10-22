package com.warehouse.repository.database.entity


import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.warehouse.repository.model.Contact
import com.warehouse.repository.model.Price
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*

@Entity(tableName = "Request", foreignKeys = [ForeignKey(
    entity = UserDTO::class,
    parentColumns = arrayOf("userID"),
    childColumns = arrayOf("userID"),
    onDelete = CASCADE
)]
)
data class RequestDTO(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "requestID")
    val requestID: Int = 0,
    @ColumnInfo(name = "userID")
    var userID: Int,
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