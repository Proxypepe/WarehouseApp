package com.warehouse.presentation.screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.warehouse.domain.ExchangeViewModel
import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Price


@Composable
fun LoadingScreen(requestViewModel: RequestViewModel, exchangeViewModel: ExchangeViewModel, navController: NavController, id: Int) {
    val loading = exchangeViewModel.loading.observeAsState()
    val price = exchangeViewModel.price.observeAsState()
    val requestDto = requestViewModel.getRequestById(id).asLiveData()
    val requestDTO: RequestDTO by requestDto.observeAsState(
        RequestDTO(0, "", 0, 0,"", null, null, null)
    )
    Log.d("DD", "req $requestDTO")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = 100.dp, start = 50.dp,
            end = 50.dp, bottom = 50.dp)
    ) {
        Column {
//            CircularProgressBar(1f, 100)
            loading.value?.let { LoadProgress(it) }
            Spacer(modifier = Modifier.padding(10.dp))
            Button(
                onClick = {
                    val _price = price.value
                    if (_price != null)  {
                        Log.d("Loading", _price.currency)
                        requestViewModel.reCreateRequestByPrice(requestDTO, Price(
                            _price.price, _price.currency)
                        )
                        requestViewModel.update()
                    }
                    navController.navigate("Requests")
                }
            ) {
                Text( text = "All right")

            }

        }
    }
}


@Composable
fun LoadProgress(
    isDisplayed: Boolean,
) {
    if (isDisplayed) {
        Row (modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
            horizontalArrangement = Arrangement.Center
        ){
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary
            )
        }
    }

}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    stokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0

    ) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec =  tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    var navigate1=  mutableStateOf(false)



    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ){
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(stokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )

    }
}