package com.warehouse.presentation.screens

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.domain.SignupViewModel
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.presentation.activity.SignUpInActivity
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.remote.repository.RegisterRepository

@Composable
fun SighupScreen(navController: NavController, signupViewModel: SignupViewModel?) {

    val fullName = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }

    val context = LocalContext.current

    Box( contentAlignment = Alignment.Center, modifier = Modifier
        .padding(top = 100.dp, start = 50.dp,
            end = 50.dp, bottom = 50.dp )
    ){
        Column (modifier = Modifier
            .fillMaxSize(),
        ) {
            Button( onClick = { navController.navigate("Login") }) {
                Text("Login")
            }

            Spacer(modifier = Modifier.padding(10.dp))

            TextField(value = fullName.value, onValueChange = {
                fullName.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                label =  { Text("Full name", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your full name...")},
                shape = RoundedCornerShape(8.dp)
            )

            TextField(value = email.value, onValueChange = {
                email.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                label =  { Text("Email", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your email...")},
                shape = RoundedCornerShape(8.dp)
            )

            TextField(value = password.value, onValueChange = {
                password.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                label =  { Text("Password", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your password...")},
                shape = RoundedCornerShape(8.dp)
            )

            TextField(value = confirmPassword.value, onValueChange = {
                confirmPassword.value = it}, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                label =  { Text("Confirm password", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Confirm your password...")},
                shape = RoundedCornerShape(8.dp)
            )

            Button( onClick = { createAcc(fullName.value.text, email.value.text, password.value.text,
                signupViewModel!!, context) },
                modifier = Modifier.align(Alignment.End)) { Text("SIGN UP")}
            Spacer(modifier = Modifier.padding(top = 150.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text("Already have an account?",
                    color = Color.Gray)
                Spacer(modifier = Modifier.padding(end = 5.dp))
                Text("Sign in", color = Color.Blue, modifier = Modifier
                    .clickable { navController.navigate("Login")})
            }
        }
    }
}

fun createAcc(fullName: String, email: String, password: String, signupViewModel: SignupViewModel, context: Context){
    val user = UserDTO(fullname = fullName, email = email, password = password, role = "single_user")
    signupViewModel.setFullState(fullName, email, password,  password)
    signupViewModel.createUser(context)

//    }
//    context.startActivity(intent)
//    val intent = Intent(context as SignUpInActivity, MainActivity::class.java).apply {
//        putExtra("email", user.email)
}


@Preview(showBackground = true)
@Composable
fun SighupScreenPreview() {
    val navController = rememberNavController()
//    val signupViewModel = SignupViewModel(null, null)
    SighupScreen(navController, null)
}