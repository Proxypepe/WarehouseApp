package com.warehouse.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.warehouse.domain.LoginViewModel
import com.warehouse.domain.LoginViewModelFactory
import com.warehouse.domain.SignupViewModel
import com.warehouse.domain.SignupViewModelFactory
import com.warehouse.presentation.screens.LoginScreen
import com.warehouse.presentation.screens.SighupScreen
import com.warehouse.repository.RequestsApplication

class SignUpInActivity : AppCompatActivity() {

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory((application as RequestsApplication).repository)
    }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory((application as RequestsApplication).repository)
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fullUri = intent.data
        var start = "Login"
        setContent {
            val navController = rememberAnimatedNavController()
            fullUri?.let {
                start = "App"
            }
            AnimatedNavHost(navController = navController, startDestination = start) {
                composable("Login", exitTransition = { _, target ->
                    if (target.destination.hierarchy.any { it.route == "Login" }) {
                        slideOutHorizontally(targetOffsetX = { -1000 })
                    } else {
                        null
                    }
                }) { LoginScreen(navController, loginViewModel) }
                composable("Signup") { SighupScreen(navController, signupViewModel) }
                // add router for sharing
            }
        }
    }
}