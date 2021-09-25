package com.warehouse.repository.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.parcelize.Parcelize


@Parcelize
data class Contact(
    @ColumnInfo(name = "contact_name")
    val name: String,
    @ColumnInfo(name = "contact_number")
    val phoneNumber: String
) : Parcelable
