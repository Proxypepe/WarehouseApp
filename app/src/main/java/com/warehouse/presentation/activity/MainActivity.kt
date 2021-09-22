package com.warehouse.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding

import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.presentation.screens.DetailScreen
import com.warehouse.presentation.screens.MakeRequestScreen
import com.warehouse.presentation.screens.StartCardViewList
import com.warehouse.repository.RequestsApplication
import com.warehouse.repository.database.entity.RequestDTO
import com.warehouse.repository.model.Request


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
                ) {  innerPadding ->
                    NavHost(navController = navController, startDestination = "Requests") {
                        composable("Requests") { Box(modifier = Modifier.padding(innerPadding))
                                                { StartCardViewList(navController, requestViewModel)}}
                        composable("Make request") { MakeRequestScreen(navController, requestViewModel)}
                        composable("details") {
                            navController.previousBackStackEntry?.arguments?.getParcelable<Request>("REQUEST")?.let {
                                DetailScreen(request = it)
                            }
                        }
                    }
                }
            }
        }
    }
}
