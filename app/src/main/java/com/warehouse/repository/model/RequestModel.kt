package com.warehouse.repository.model

data class RequestModel (
    val userId: Int,
    val productName: String,
    val amount: Int,
    val warehousePlace: Int,
    val status: String,
    val contact: Contact?,
    val price: Price?
)

data class RequestResponseModel (
    val requestID: Int,
    val userId: Int,
    val productName: String,
    val amount: Int,
    val warehousePlace: Int,
    val status: String,
    val contact: Contact?,
    val price: Price?
)