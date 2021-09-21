package com.warehouse.presentation.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.theme.ComposeTestTheme
import com.warehouse.repository.database.entity.Request

@Composable
fun StartCardViewList(requestViewModel: RequestViewModel) {
    val list: List<Request> by requestViewModel.allRequest.observeAsState(initial = emptyList())
    CardViewList(list)
}

@Composable
fun CardView(request: Request) {
    Card {
        Column(
            Modifier.fillMaxWidth()
                .padding(10.dp)
        ) {
            Row {
                Text(text = "Request id", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.id.toString())
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
                Text(text = "Arrival date", modifier = Modifier.width(250.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = request.arrivalDate.toString())
            }
        }
    }
}

@Composable
fun CardViewList(requests: List<Request>) {
    LazyColumn {
        items(requests) { request ->
            CardView(request)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewPreview() {
    ComposeTestTheme {
        CardView(Request(0, "Hello", 10, 2,"Android", null))
    }
}

@Preview(showBackground = true)
@Composable
fun CardViewListPreview() {
    val l :List<Request> = arrayListOf(
        Request(0, "Hello1", 10, 2,"Android", null),
        Request(1, "Hello2", 131, 1,"Arraved", null),
        Request(2, "Hello3", 1210, 3,"sa", null)
    )
    ComposeTestTheme {
        CardViewList(l)
    }
}

