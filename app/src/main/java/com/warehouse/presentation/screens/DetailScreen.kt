package com.warehouse.presentation.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.Request
import com.warehouse.repository.model.toRequestDTO


@Composable
fun DetailFromDeep(requestViewModel: RequestViewModel, id: Int?) {
    val requests by  requestViewModel.allRequests!!.observeAsState()
    val requestsSize  = requests?.size

    if (id != null &&  requestsSize != null) {
        if (id > requestsSize || id < 0) {
            ErrorScreen()
        } else {
            val requestFromBD = requestViewModel.getRequestById(id)
            val liveData = requestFromBD.asLiveData()
            val requestDTO: RequestDTO by liveData.observeAsState(
                RequestDTO(0, 0, "", 0, 0,"", null, null, null))
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                CardView(requestDTO, null)
            }
        }
    } else {
        ErrorScreen()
    }
}

@Composable
fun DetailScreen (request: Request, navController: NavController, user: UserDTO){
    val context = LocalContext.current
    val req = toRequestDTO(request)
    Column (
        modifier = Modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
            ){
        CardView(req)
        Spacer(modifier = Modifier.padding(top=10.dp))
        Row {
            Button(onClick = { getShareIntend(context, request.id)},
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.White),
                modifier = Modifier.border(BorderStroke(0.dp, Color.White))) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color(0xFFCC3333))
                Text(text = "Share me")
            }
            Spacer(modifier = Modifier.padding(5.dp))
            Button(onClick =  {
                navController.navigate("details/change/${request.id}")
            }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White),
                modifier = Modifier.border(BorderStroke(0.dp, Color.White))) {

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color(0xFFCC3333))
                Spacer(modifier = Modifier.padding(top=10.dp))
                Text(text = "Change currency")
            }
        }
        if( user.role == "moderator" || user.role == "admin")
        {
            Spacer(modifier = Modifier.padding(top=10.dp))
            Button(onClick =  {
                navController.navigate("details/change/moder/${request.id}")
            }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White),
                modifier = Modifier.border(BorderStroke(0.dp, Color.White))) {

                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color(0xFFCC3333))
                Spacer(modifier = Modifier.padding(top=10.dp))
                Text(text = "Change Fields")
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Box (modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text ("Info not found", fontSize = 30.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun DetailScreenPreview () {
    val navController = rememberNavController()
    DetailScreen(Request(0, 0,"Hello", 10, 2, "Android", null, null, null),
        navController, UserDTO(1, "","", "", "moderator"))
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview(){
    ErrorScreen()
}


fun getShareIntend(context: Context, id: Int){
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.warehouse_app.com/detail?id=$id")
    sendIntent.type = "text/plain"

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}