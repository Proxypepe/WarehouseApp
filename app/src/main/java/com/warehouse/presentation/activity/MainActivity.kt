package com.warehouse.presentation.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


import com.warehouse.domain.*

import com.warehouse.presentation.navigation.AppNavigation
import com.warehouse.presentation.screens.*
import com.warehouse.repository.RequestsApplication



class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    private val exchangeViewModel: ExchangeViewModel by viewModels {
        ExchangeViewModelFactory((application as RequestsApplication).exchangeApi)
    }


    private var contactViewModel = ContactViewModel()

    private var loginViewModel = LoginViewModel()
    private var signupViewModel = SignupViewModel()


    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fullUri = intent.data
        var start = "Login"
        exchangeViewModel
        setContent {
            val navController = rememberAnimatedNavController()
            fullUri?.let {
                start = "App"
            }
            AnimatedNavHost(navController = navController, startDestination = start){
                composable("Login", exitTransition = { _, target ->
                    if (target.destination.hierarchy.any { it.route == "Login"}) {
                     slideOutHorizontally(targetOffsetX = { - 1000 })
                    }else {
                        null
                    }
                })  { LoginScreen(navController, loginViewModel) }
                composable("Signup") { SighupScreen(navController, signupViewModel) }
                composable("App", enterTransition = { _, _ ->
                    fadeIn(animationSpec = tween(2000))

                }) {
                    AppNavigation(requestViewModel, contactViewModel, exchangeViewModel ,fullUri)}
            }
        }
    }
}
