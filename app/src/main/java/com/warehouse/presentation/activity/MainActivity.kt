package com.warehouse.presentation.activity


import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding

import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.warehouse.domain.*

import com.warehouse.presentation.navigation.AppNavigation
import com.warehouse.presentation.screens.*
import com.warehouse.repository.RequestsApplication
import com.warehouse.repository.model.Request



class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    private var contactViewModel = ContactViewModel()

    private var loginViewModel = LoginViewModel()
    private var signupViewModel = SignupViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fullUri = intent.data
        var start = "Login"

        setContent {
            val navController = rememberNavController()
            fullUri?.let {
                start = "App"
            }
            NavHost(navController = navController, startDestination = start){
                composable("Login")  { LoginScreen(navController, loginViewModel) }
                composable("Signup") { SighupScreen(navController, signupViewModel) }
                composable("App") { AppNavigation(requestViewModel, contactViewModel, fullUri)}
            }


        }
    }
}
