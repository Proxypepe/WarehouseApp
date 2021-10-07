package com.warehouse.presentation.screens

import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData

import com.warehouse.domain.ExchangeViewModel
import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Price


@Composable
fun PriceChooseScreen(navController: NavController, requestViewModel: RequestViewModel?,
                      exchangeViewModel: ExchangeViewModel?, id: Int) {
    val context = LocalContext.current
    val priceValue = remember { mutableStateOf(TextFieldValue()) }

    val requestDto = requestViewModel?.getRequestById(id)
    if (requestDto == null)
    {
        Text("Something went wrong")
    } else{
        val liveData = requestDto.asLiveData()
        val requestDTO: RequestDTO by liveData.observeAsState(
            RequestDTO(0, "", 0, 0,"", null, null, null))
        val prevCurrency = requestDTO.price?.currency

        Column( modifier = Modifier
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text( text = "Current value: ${requestDTO.price?.price ?: "Unknown"} " +
                    (requestDTO.price?.currency ?: "")
            )

            Row{
                TextField(value = priceValue.value, onValueChange = {
                    priceValue.value = it}, modifier = Modifier
                    .width(300.dp)
                    .padding(10.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    placeholder = { Text ("Enter new price...")},
                    shape = RoundedCornerShape(8.dp))

                Dropdown(requestViewModel)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button( onClick = {
                if (prevCurrency != null) {
                    Log.d("PriceChoose", "Prev $prevCurrency amount ${priceValue.value.text} new ${requestViewModel.getPriceBase()!!}")
                    exchangeViewModel?.getPrice(priceValue.value.text, prevCurrency,
                        requestViewModel.getPriceBase()!!)
                    navController.navigate("loading/$id")
                } else {
                    Log.d("PriceChoose", "GG")
                }

            })
            {



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PriceChooseScreenPreview() {
    val navController = rememberNavController()
    PriceChooseScreen(navController, null, null, 0 )
}

fun makeExchangeRequest(amount: String, from: String, to: String, requestViewModel: RequestViewModel?,
                        exchangeViewModel: ExchangeViewModel?, context: Context
){

//    val price: LiveData<Price>? = exchangeViewModel?.fetchExchange(amount, from, to)
//
//    Log.d("nullable", (price ?: "Nell") as String)
//    price?.let {
//        Log.d("price", "${price.price}  ${price.currency}")
//        requestViewModel?.reCreateRequestByPrice(it)
//        requestViewModel?.update()
//    }
}
//val req = requestViewModel.getPriceBase()
//req?.let {
//    Log.d("Curr", "new ${req}  old $prevCurrency" )
//    if (prevCurrency != null) {
//
//        val p = exchangeViewModel?.getPrice(priceValue.value.text, prevCurrency,
//            requestViewModel.getPriceBase()!!
//        )
//
//        Log.d("Rwqwqwq", p?.currency ?: " dds")
//
//        makeExchangeRequest(priceValue.value.text,
//            prevCurrency, it,  requestViewModel, exchangeViewModel, context)