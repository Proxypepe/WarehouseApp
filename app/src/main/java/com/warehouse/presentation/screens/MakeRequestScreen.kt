package com.warehouse.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.theme.ComposeTestTheme
import java.util.*


@Composable
fun MakeRequestScreen(navController: NavController, requestViewModel: RequestViewModel) {
    val productName = remember { mutableStateOf(TextFieldValue()) }
    val amount = remember { mutableStateOf(TextFieldValue()) }
    val warehousePlace = remember { mutableStateOf(TextFieldValue()) }
    val status = remember { mutableStateOf(TextFieldValue()) }
    val arrivalDate = remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextField(value = productName.value, onValueChange = {
            productName.value = it}, modifier = Modifier.fillMaxWidth().padding(10.dp),
            placeholder = { Text ("Enter product name...")},
            shape = RoundedCornerShape(8.dp))

        TextField(value = amount.value, onValueChange = {
            amount.value = it}, modifier = Modifier.fillMaxWidth().padding(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text ("Enter the quantity of the product...")},
            shape = RoundedCornerShape(8.dp))

        TextField(value = warehousePlace.value, onValueChange = {
            warehousePlace.value = it}, modifier = Modifier.fillMaxWidth().padding(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text ("Enter the warehouse location number...")},
            shape = RoundedCornerShape(8.dp))

        TextField(value = status.value, onValueChange = {
            status.value = it}, modifier = Modifier.fillMaxWidth().padding(10.dp),
            placeholder = { Text ("Enter the product status...")},
            shape = RoundedCornerShape(8.dp))

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if (productName.value.text.isNotEmpty() && amount.value.text.isNotEmpty() &&
                warehousePlace.value.text.isNotEmpty() &&  status.value.text.isNotEmpty()) {
                makeRequest(
                    requestViewModel,
                    productName.value.text,
                    Integer.parseInt(amount.value.text),
                    Integer.parseInt(warehousePlace.value.text),
                    status.value.text,
                    null
                )
                navController.navigate("Requests")
            } else {
                Toast.makeText( context,
                    "Fill in all the fields",  Toast.LENGTH_LONG).show()
            }
        })
        { Text("Add request")}
    }
}


@Preview(showBackground = true)
@Composable
fun MakeRequestScreenPreview() {
    ComposeTestTheme {
        // MakeRequestScreen()
    }
}

fun makeRequest(requestViewModel: RequestViewModel, productName: String, amount: Int, warehousePlace:Int,
                status: String, date: Date?) {
    requestViewModel.setRequest(productName, amount, warehousePlace, status, date)
    requestViewModel.writeRequest()
}
