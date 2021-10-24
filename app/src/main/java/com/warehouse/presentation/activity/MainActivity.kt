package com.warehouse.presentation.activity


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.Text
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

    private val adminViewModel: AdminViewModel by viewModels {
        AdminViewModelFactory((application as RequestsApplication).repository)
    }

    private val exchangeViewModel: ExchangeViewModel by viewModels {
        ExchangeViewModelFactory((application as RequestsApplication).remoteRepository)
    }

    private var contactViewModel = ContactViewModel()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val userId = intent.getIntExtra("User_Id", 0)
        requestViewModel.setUserId(userId)


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
