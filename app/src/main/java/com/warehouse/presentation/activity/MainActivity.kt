package com.warehouse.presentation.activity


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Text
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination.Companion.hierarchy
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


import com.warehouse.domain.*

import com.warehouse.presentation.navigation.AppNavigation
import com.warehouse.presentation.screens.*
import com.warehouse.repository.RequestsApplication
import java.lang.Thread.sleep


class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    private val adminViewModel: AdminViewModel by viewModels {
        AdminViewModelFactory((application as RequestsApplication).repository)
    }

    private val exchangeViewModel: ExchangeViewModel by viewModels {
        ExchangeViewModelFactory((application as RequestsApplication).remoteRepository)
    }

    private var contactViewModel = ContactViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val userId = intent.getIntExtra("User_Id", 0)
        val role = intent.getStringExtra("role") ?: "single_user"
        val email = intent.getStringExtra("email")

        if (email == null){
            requestViewModel.setUserId(userId, role)
            Log.d("Without email", "Not email log")
        } else {
            requestViewModel.initUser(email)
            Log.d("Email", "Email log")
        }
        sleep(3000)
    }

    @ExperimentalAnimationApi
    override fun onStart() {
        super.onStart()

        setContent {
            AppNavigation(
                requestViewModel = requestViewModel,
                contactViewModel = contactViewModel,
                exchangeViewModel = exchangeViewModel,
                adminViewModel = adminViewModel
            )
        }
    }
}


