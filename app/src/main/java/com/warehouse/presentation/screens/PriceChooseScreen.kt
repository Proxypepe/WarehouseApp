package com.warehouse.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData

import com.warehouse.domain.ExchangeViewModel
import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO


@Composable
fun PriceChooseScreen(navController: NavController, requestViewModel: RequestViewModel?,
                      exchangeViewModel: ExchangeViewModel?, id: Int) {
//    val context = LocalContext.current
//    val priceValue = remember { mutableStateOf(TextFieldValue()) }

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
            .fillMaxHeight().padding(start = 135.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text( text = "Current value: ${"%.2f".format(requestDTO.price?.price)} " +
                    (requestDTO.price?.currency ?: "")
            )
            Dropdown(requestViewModel)

//            Row{
//                TextField(value = priceValue.value, onValueChange = {
//                    priceValue.value = it}, modifier = Modifier
//                    .width(300.dp)
//                    .padding(10.dp),
//                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
//                    placeholder = { Text ("Enter new price...")},
//                    shape = RoundedCornerShape(8.dp))
//
//                Dropdown(requestViewModel)
//            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button( onClick = {
                val base = requestViewModel.getPriceBase()
                if (prevCurrency != null && prevCurrency != base
                    &&  base != null) {
                    val _anount =  "%.2f".format(requestDTO.price?.price).toDouble()
//                    Log.d("PriceChoose", "Prev $prevCurrency amount $_anount new ${base}")
                    exchangeViewModel?.getPrice(_anount.toString(), prevCurrency,
                        base)
                    navController.navigate("loading/$id")
                } else {
                    Log.d("PriceChoose", "GG")
                }
            })
            {
                Text("Change")
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

//fun makeExchangeRequest(amount: String, from: String, to: String, requestViewModel: RequestViewModel?,
//                        exchangeViewModel: ExchangeViewModel?, context: Context
//){

//    val price: LiveData<Price>? = exchangeViewModel?.fetchExchange(amount, from, to)
//
//    Log.d("nullable", (price ?: "Nell") as String)
//    price?.let {
//        Log.d("price", "${price.price}  ${price.currency}")
//        requestViewModel?.reCreateRequestByPrice(it)
//        requestViewModel?.update()
//    }
//}
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