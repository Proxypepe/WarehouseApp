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
                    val arrivalDate: Date?,
                    val contact: Contact?,
                    val price: Price?) : Parcelable

fun toRequest(requestDto: RequestDTO): Request {
    return Request(requestDto.requestID, requestDto.productName, requestDto.amount, requestDto.warehousePlace,
        requestDto.status, requestDto.arrivalDate, requestDto.contact, requestDto.price)
}

fun toRequestDTO(request: Request): RequestDTO {
    return RequestDTO(request.id, 0, request.productName, request.amount, request.warehousePlace,
        request.status, request.arrivalDate, request.contact, request.price)
}