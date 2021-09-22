package com.warehouse.repository.model


import android.os.Parcelable
import com.warehouse.repository.database.entity.RequestDTO
import kotlinx.parcelize.Parcelize

import java.util.*

@Parcelize
data class Request(val id: Int = 0,
                    val productName: String,
                    val amount: Int,
                    val warehousePlace: Int,
                    val status: String,
                    val arrivalDate: Date?) : Parcelable

fun toRequest(requestDto: RequestDTO): Request {
    return Request(requestDto.id, requestDto.productName, requestDto.amount, requestDto.warehousePlace,
        requestDto.status, requestDto.arrivalDate)
}

fun toRequestDTO(request: Request): RequestDTO {
    return RequestDTO(request.id, request.productName, request.amount, request.warehousePlace,
        request.status, request.arrivalDate)
}