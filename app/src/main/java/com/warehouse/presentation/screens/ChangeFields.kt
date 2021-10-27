package com.warehouse.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO

@Composable
fun ChangeFields(requestViewModel: RequestViewModel?, id: Int, navController: NavController){

    val productName     = remember { mutableStateOf(TextFieldValue()) }
    val amount          = remember { mutableStateOf(TextFieldValue()) }
    val warehousePlace  = remember { mutableStateOf(TextFieldValue()) }
    val status          = remember { mutableStateOf(TextFieldValue()) }

    val fullRequest: RequestDTO by requestViewModel?.getRequestById(id)!!.asLiveData().observeAsState(initial = RequestDTO(0, 1, "", 1, 1, "",
        null, null, null))

    Column (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = productName.value, onValueChange = {
                productName.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder = { Text("Enter product name...") },
            shape = RoundedCornerShape(8.dp)
        )

        TextField(
            value = amount.value, onValueChange = {
                amount.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text("Enter the quantity of the product...") },
            shape = RoundedCornerShape(8.dp)
        )

        TextField(
            value = warehousePlace.value, onValueChange = {
                warehousePlace.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            placeholder = { Text("Enter the warehouse location number...") },
            shape = RoundedCornerShape(8.dp)
        )

        TextField(
            value = status.value, onValueChange = {
                status.value = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            placeholder = { Text("Enter the product status...") },
            shape = RoundedCornerShape(8.dp)
        )

        Button( onClick = {
            val prod = if (productName.value.text == "")
            {
                fullRequest.productName
            } else {
                productName.value.text
            }
            val am = if (amount.value.text == "")
            {
                fullRequest.amount
            } else {
                amount.value.text.toInt()
            }

            val wP = if (warehousePlace.value.text == "")
            {
                fullRequest.warehousePlace
            } else {
                warehousePlace.value.text.toInt()
            }
            val s = if (status.value.text == "")
            {
                fullRequest.status
            } else {
                status.value.text
            }

            val newRecord = RequestDTO(fullRequest.requestID, fullRequest.userID, prod, am, wP, s,
                fullRequest.arrivalDate, fullRequest.contact, fullRequest.price)
            requestViewModel?.update(newRecord)
            navController.navigate("Requests")

        }) {
            Text("Change")
        }

    }




}




@Preview(showBackground = true)
@Composable
fun ChangeFieldsPreview() {
    val navController = rememberNavController()
    ChangeFields(requestViewModel = null,0,  navController)
}