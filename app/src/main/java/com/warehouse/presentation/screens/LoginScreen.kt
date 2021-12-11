package com.warehouse.presentation.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.R
import com.warehouse.domain.LoginViewModel
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.presentation.activity.SignUpInActivity
import com.warehouse.repository.database.entity.UserDTO
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignInClient


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel, mGoogleSignInClient: GoogleSignInClient?, RC_SIGN_IN: Int) {

    val login = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }


    val context = LocalContext.current

    Box( contentAlignment = Alignment.Center, modifier = Modifier
        .padding(top = 100.dp, start = 50.dp,
            end = 50.dp, bottom = 50.dp )
        ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Text("Login",
                modifier = Modifier.fillMaxWidth(), fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.padding(5.dp))
            Text("Please sign in to continue.",
                modifier = Modifier.fillMaxWidth(), color = Color.Gray)
            Spacer(modifier = Modifier.padding(25.dp))
            TextField(value = login.value, onValueChange = {
                login.value = it}, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
                label =  { Text("Email", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your email...")},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TextField(value = password.value, onValueChange = {
                password.value = it}, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
                visualTransformation = PasswordVisualTransformation(),
                label =  { Text("Password", fontSize = 10.sp) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White),
                placeholder = { Text ("Enter your password...")},
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Button( onClick = {
                var user: UserDTO? = null
                var access: Boolean = false
                loginViewModel.setFullState(login.value.text, password.value.text)
                loginViewModel.getUserByEmail(login.value.text)?.asLiveData()?.observe(context as (SignUpInActivity), {
                    if ( it != null){
                        if (it.email == login.value.text && it.password == password.value.text)
                        {
                            user = it
                            access = true
                        } else {
                            user = null
                            access = false
                            Toast.makeText(context, "Uncorrect Email or password", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(context, "Uncorrect Email or password", Toast.LENGTH_LONG).show()
                    }
                    if (access)
                    {
                        val intent = Intent(context, MainActivity::class.java).apply {
                            user?.let { it1 ->
                                putExtra("User_Id", it1.userID)
                                putExtra("role", it1.role)}
                        }
                        context.startActivity(intent)
                    }
                })

                },
                modifier = Modifier.align(Alignment.End)) { Text("LOGIN")}
            Button( onClick = {
                signIn(context, mGoogleSignInClient!!, RC_SIGN_IN)
            },
                colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.White),
                modifier = Modifier.border(BorderStroke(0.dp, Color.White)) ) {
                Icon(
                    painter =  painterResource(id = R.drawable.ic_google_logo),
                    contentDescription = "Localized description",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Unspecified)
                Spacer(modifier = Modifier.padding(top=10.dp))
                Text(text = "Sign In")
            }
            Spacer(modifier = Modifier.padding(top = 200.dp))
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

@Composable
fun RunProgress(loginViewModel: LoginViewModel) {
    val loading = loginViewModel.loading.observeAsState()
    loading.value?.let { LoadProgress(it) }
}



private fun signIn(context: Context, mGoogleSignInClient: GoogleSignInClient, RC_SIGN_IN: Int) {
    val signInIntent: Intent = mGoogleSignInClient.signInIntent
    (context as SignUpInActivity).startActivityForResult(signInIntent, RC_SIGN_IN)
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    val loginViewModel = LoginViewModel(null, null)

    LoginScreen(navController, loginViewModel, null, 0)
}