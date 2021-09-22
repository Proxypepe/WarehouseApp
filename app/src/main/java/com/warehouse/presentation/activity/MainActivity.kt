package com.warehouse.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import androidx.compose.material.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.presentation.screens.MakeRequestScreen
import com.warehouse.presentation.screens.StartCardViewList
import com.warehouse.repository.RequestsApplication


class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colors.background) {
                val bottomItems = listOf("Requests", "Make request")
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigation {
                            bottomItems.forEach { screen ->
                                BottomNavigationItem(selected = false,
                                    onClick = { navController.navigate(screen) },
                                    label =  { Text (screen)}, icon = { })
                            }// Foreach
                        } //BottomNavigation
                    } // bottomBar
                ) {
                    NavHost(navController = navController, startDestination = "Requests") {
                        composable("Requests") { StartCardViewList(requestViewModel)}
                        composable("Make request") { MakeRequestScreen(navController, requestViewModel)}
                    }
                }
            }
        }
    }
}
