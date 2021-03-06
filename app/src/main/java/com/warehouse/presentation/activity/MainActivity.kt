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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.warehouse.domain.*

import com.warehouse.presentation.navigation.AppNavigation
import com.warehouse.presentation.screens.*
import com.warehouse.repository.RequestsApplication
import com.warehouse.repository.database.entity.UserDTO
import com.warehouse.repository.model.ExchangeItem
import com.warehouse.repository.remote.api.UserApi
import com.warehouse.repository.remote.repository.UserRepository
import java.lang.Thread.sleep


class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository,
        (application as RequestsApplication).requestsRepository,
        (application as RequestsApplication).userRepository)
    }

    private val adminViewModel: AdminViewModel by viewModels {
        AdminViewModelFactory((application as RequestsApplication).repository,
            (application as RequestsApplication).userRepository)
    }

    private val exchangeViewModel: ExchangeViewModel by viewModels {
        ExchangeViewModelFactory((application as RequestsApplication).remoteRepository)
    }

    private var contactViewModel = ContactViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        val userId = intent.getIntExtra("userId", 0)
        val role = intent.getStringExtra("role") ?: "single_user"

        Log.d("User data from intent", "$userId, $role")

        if(!requestViewModel.gotFromRemote) {
            requestViewModel.getRequestsByUserId(userId)
            requestViewModel.role = role
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


