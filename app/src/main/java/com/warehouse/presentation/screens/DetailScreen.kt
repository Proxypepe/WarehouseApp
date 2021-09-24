package com.warehouse.presentation.screens

import android.content.Context
import android.content.Intent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData

import com.warehouse.domain.RequestViewModel
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Request
import com.warehouse.repository.model.toRequest
import com.warehouse.repository.model.toRequestDTO


@Composable
fun DetailFromDeep(requestViewModel: RequestViewModel, id: Int?) {
    val requests by  requestViewModel.allRequests.observeAsState()
    val requestsSize  = requests?.size

    if (id != null &&  requestsSize != null) {
        if (id > requestsSize || id < 0) {
            Text ("Info not found")
        } else {
            val requestFromBD = requestViewModel.getRequestById(id)
            val liveData = requestFromBD.asLiveData()
            val requestDTO: RequestDTO by liveData.observeAsState(RequestDTO(0, "", 0, 0,"", null))
            val request: Request = toRequest(requestDTO)
            DetailScreen(request = request)
        }
    } else {
        Text ("Info not found")
    }

}

@Composable
fun DetailScreen (request: Request) {
    val context = LocalContext.current
    val req = toRequestDTO(request)
    Column {
        CardView(req)
        Spacer(modifier = Modifier.padding(top=10.dp))
        Button(onClick = { getShareIntend(context, request.id)}) { Text("Click Me")}
    }
}



@Preview(showBackground = true)
@Composable
fun DetailScreenPreview () {
    Column {
        CardView(RequestDTO(0, "Hello", 10, 2, "Android", null))

    }
}


fun getShareIntend(context: Context, id: Int){
    val sendIntent = Intent()
    sendIntent.action = Intent.ACTION_SEND
    sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.warehouse_app.com/detail?id=$id")
    sendIntent.type = "text/plain"

    val shareIntent = Intent.createChooser(sendIntent, null)
    context.startActivity(shareIntent)
}