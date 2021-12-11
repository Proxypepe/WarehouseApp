package com.warehouse.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asLiveData
import com.warehouse.domain.AdminViewModel
import com.warehouse.repository.database.entity.UserDTO

@Composable
fun AdminPanel(adminViewModel: AdminViewModel?) {
    val users: List<UserDTO> by adminViewModel?.allData!!.asLiveData().observeAsState(initial = emptyList())

    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(users) { user ->
            UserCard(adminViewModel, user)
        }
    }
}

@Composable
fun UserCard(adminViewModel: AdminViewModel?, userData: UserDTO) {
    val newRole= remember { mutableStateOf(TextFieldValue()) }
    val context = LocalContext.current

    Card(elevation = 5.dp, modifier = Modifier.fillMaxWidth()){
        Row {
            Column {
                Row(modifier = Modifier.padding(10.dp)) {
                    Text("Full name", modifier = Modifier.width(200.dp))
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(userData.fullname)
                }// Row

                Row(modifier = Modifier.padding(10.dp)) {
                    Text("Email", modifier = Modifier.width(200.dp))
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(userData.email)
                }// Row

                Row(modifier = Modifier.padding(10.dp)) {
                    Text("Current role", modifier = Modifier.width(200.dp))
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(userData.role)
                } //Row
                Row(modifier = Modifier.padding(10.dp)){
                    // Change to Dropbox ?
                    TextField(value = newRole.value, modifier = Modifier
                        .height(55.dp)
                        .width(250.dp), onValueChange = {
                        newRole.value = it},
                        placeholder = { Text ("Enter new role...")},
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.White),
                        modifier = Modifier
                            .border(BorderStroke(0.dp, Color.White))
                            .padding(start = 5.dp, top = 10.dp),
                        onClick = {
                            if (newRole.value.text == "admin" || newRole.value.text == "moderator" || newRole.value.text == "single_user")
                            {
                                adminViewModel?.updateUser(userData, newRole.value.text)
                                adminViewModel?.getAllUsers()
                            } else {
                                Toast.makeText(context, "Invalid role", Toast.LENGTH_LONG).show()
                            }
                        }) {
                        Text("Change")
                    }
                }
            }// Column
        }// Row
    }// Card
}


@Preview(showBackground = true)
@Composable
fun AdminPanelPreview() {

    AdminPanel(null)
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    val user = UserDTO(1, "Hell ye", "gsdgf", "", "single_user")
    UserCard(null, user)
}