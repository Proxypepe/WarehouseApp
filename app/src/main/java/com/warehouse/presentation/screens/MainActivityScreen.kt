package com.warehouse.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.theme.ComposeTestTheme
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Price
import com.warehouse.repository.model.toRequest


@Composable
fun StartCardViewList(navController: NavController, requestViewModel: RequestViewModel) {
    if (requestViewModel.allRequests != null)
    {
        val list: List<RequestDTO> by requestViewModel.allRequests!!.observeAsState(initial = emptyList())
        Log.d("Request list card view", list.toString())
        CardViewList(list, navController)
    }
}

@Composable
fun CardViewList(requests: List<RequestDTO>, navController: NavController? = null) {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(requests) { request ->
            CardView(request, navController)
        }
    }
}

@Composable
fun CardView(request: RequestDTO, navController: NavController? = null) {
    Card (
        elevation = 8.dp,
        modifier = Modifier.clickable {
            navController?.navigate("details", bundleOf("REQUEST" to toRequest(request)))
        }
    ){
        Column(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Text(text = "Request id", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.requestID.toString())
            }
            Row {
                Text(text = "Product name", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.productName)
            }
            Row {
                Text(text = "Amount", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.amount.toString())
            }
            Row {
                Text(text = "Warehouse place", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.warehousePlace.toString())
            }
            Row {
                Text(text = "Status", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.status)
            }
            Row {
                Text(text = "Supplier name", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.contact?.name ?: "Unknown")
            }
            Row {
                Text(text = "Supplier phone number", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.contact?.phoneNumber ?: "Unknown")
            }
            Row {
                Text(text = "Price", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "%.2f".format(request.price?.price) ?: "Unknown" )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.price?.currency ?: "")
            }
        }
    }
}

fun NavController?.navigate(route: String, params: Bundle?, builder: NavOptionsBuilder.() -> Unit = {}) {
    this?.currentBackStackEntry?.arguments?.putAll(params)

    this?.navigate(route, builder)
}


@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    ComposeTestTheme {
        CardView(RequestDTO(0, 0, "Hello", 10, 2,"Android", null, null, Price(1212.21, "RUB")))
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewListPreview() {
    val l :List<RequestDTO> = arrayListOf(
        RequestDTO(0, 0,"Hello1", 10, 2,"Android", null, null, null),
        RequestDTO(1, 0, "Hello2", 131, 1,"Arraved", null, null, null),
        RequestDTO(2, 0, "Hello3", 1210, 3,"sa", null, null, null)
    )
    ComposeTestTheme {
        CardViewList(l)
    }
}
