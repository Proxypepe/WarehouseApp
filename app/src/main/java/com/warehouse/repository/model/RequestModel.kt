package com.warehouse.repository.model

data class RequestModel (
    val userId: Int,
    val productName: String,
    val amount: Int,
    val warehousePlace: Int,
    val contact: Contact?,
    val price: Price?
)