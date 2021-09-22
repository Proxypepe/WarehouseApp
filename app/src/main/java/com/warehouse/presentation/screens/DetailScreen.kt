package com.warehouse.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Request
import com.warehouse.repository.model.toRequestDTO


@Composable
fun DetailScreen (request: Request) {
    val req = toRequestDTO(request)
    Column {
        CardView(req)
    }
}



@Preview(showBackground = true)
@Composable
fun DetailScreenPreview () {
    CardView(RequestDTO(0, "Hello", 10, 2,"Android", null))

}