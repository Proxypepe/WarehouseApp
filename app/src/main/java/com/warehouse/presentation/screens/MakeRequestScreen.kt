package com.warehouse.presentation.screens


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.theme.ComposeTestTheme
import java.util.*


@Composable
fun MakeRequestScreen(navController: NavController, requestViewModel: RequestViewModel?) {
    val state = requestViewModel?.getState()
    val pN = state?.productName ?: ""
    val a: String = state?.amount ?: ""
    val w = state?.warehousePlace ?: ""
    val s = state?.status ?: ""
    val pV = state?.price_value ?: ""

    val productName     = remember { mutableStateOf(TextFieldValue(pN))}
    val amount          = remember { mutableStateOf(TextFieldValue(a)) }
    val warehousePlace  = remember { mutableStateOf(TextFieldValue(w)) }
    val status          = remember { mutableStateOf(TextFieldValue(s)) }
    val priceValue      = remember { mutableStateOf(TextFieldValue(pV))}

    val context = LocalContext.current

    
    Column (
        modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight(),
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

        Row{
            TextField(value = priceValue.value, onValueChange = {
                priceValue.value = it}, modifier = Modifier.width(300.dp).padding(10.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                placeholder = { Text ("Enter the price...")},
                shape = RoundedCornerShape(8.dp))

            Dropdown(requestViewModel)
        }


        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            requestViewModel?.saveState(
                productName.value.text, amount.value.text,
                warehousePlace.value.text, status.value.text, priceValue.value.text
            )
            navController.navigate("contacts")
        }) {
            Text("Add Contact")
        }
        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if (productName.value.text.isNotEmpty() && amount.value.text.isNotEmpty() &&
                warehousePlace.value.text.isNotEmpty() &&  status.value.text.isNotEmpty()
                && priceValue.value.text.isNotEmpty()) {
                if (requestViewModel != null) {
                    makeRequest(
                        requestViewModel,
                        productName.value.text,
                        Integer.parseInt(amount.value.text),
                        Integer.parseInt(warehousePlace.value.text),
                        status.value.text,
                        null,
                        priceValue.value.text
                    )
                }
                navController.navigate("Requests")
            } else {
                Toast.makeText( context,
                    "Fill in all the fields",  Toast.LENGTH_LONG).show()
            }
        })
        { Text("Add request")}
    }
}

@Composable
fun Dropdown(requestViewModel: RequestViewModel?) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("RUB", "USD", "GBP")
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier.padding(top=10.dp, bottom=10.dp).width(100.dp).height(50.dp)){
        Text(
            items[selectedIndex],
            modifier = Modifier.fillMaxSize().clickable(onClick = { expanded = true }).background(
                Color.Gray
            ).padding(15.dp), textAlign = TextAlign.Center
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.wrapContentSize()
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    requestViewModel?.setPriceBase(s)
                }){
                    Text(text = s)
                }
            }
        }// DropdownMenu
    }// Box
}// fun



@Preview(showBackground = true)
@Composable
fun MakeRequestScreenPreview() {
    val navController = rememberNavController()
    ComposeTestTheme {
        MakeRequestScreen(navController, null)
    }
}

fun makeRequest(requestViewModel: RequestViewModel, productName: String, amount: Int, warehousePlace:Int,
                status: String, date: Date?, price: String) {
    requestViewModel.setRequest(productName, amount, warehousePlace, status, date, price)
    requestViewModel.writeRequest()
    requestViewModel.clear()
}

