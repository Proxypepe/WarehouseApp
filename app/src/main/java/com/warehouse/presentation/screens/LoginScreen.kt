package com.warehouse.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.domain.LoginViewModel

@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val login = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    Box( contentAlignment = Alignment.Center, modifier = Modifier
        .padding(top = 100.dp, start = 50.dp,
            end = 50.dp, bottom = 50.dp )
        ){
        Column (modifier = Modifier
            .fillMaxSize(),
        ) {
            Text("Login",
                modifier = Modifier.fillMaxWidth(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(5.dp))
            Text("Please sign in to continue.",
                modifier = Modifier.fillMaxWidth(), color = Color.Gray)
            Spacer(modifier = Modifier.padding(25.dp))
            TextField(value = login.value, onValueChange = {
                login.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                label =  { Text("Email", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your email...")},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(value = password.value, onValueChange = {
                password.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                label =  { Text("Password", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your password...")},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Button( onClick = { navController.navigate("App") },
                modifier = Modifier.align(Alignment.End)) { Text("LOGIN")}
            Spacer(modifier = Modifier.padding(top = 225.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text("Don't have an account?",
                     color = Color.Gray)
                Spacer(modifier = Modifier.padding(end = 5.dp))
                Text("Sign up", color = Color.Blue, modifier = Modifier
                    .clickable { navController.navigate("Signup")})
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val loginViewModel = LoginViewModel()
    LoginScreen(navController, loginViewModel)
}