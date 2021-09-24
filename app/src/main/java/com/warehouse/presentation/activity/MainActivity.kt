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

import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.presentation.screens.DetailFromDeep
import com.warehouse.presentation.screens.DetailScreen
import com.warehouse.presentation.screens.MakeRequestScreen
import com.warehouse.presentation.screens.StartCardViewList
import com.warehouse.repository.RequestsApplication
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
                val uriPat = "www.warehouse_app.com"
                val context = LocalContext.current
                val fullUri: Uri? = getIntent().getData()
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
                        }// route detail

                        composable("detail?id={id}",
                            deepLinks = listOf(navDeepLink { uriPattern = "$uriPat/detail?id={id}" })
                        ) {
                            val tokens: List<String> = fullUri.toString().split("=")
                            val id: Int? = try { Integer.parseInt(tokens[1])} catch (e: Exception) { null }
                            DetailFromDeep(requestViewModel, id)

                        }// Deep link handler
                    }// NavHost
                }// Scaffold
            }// Surface
        }
    }
}
