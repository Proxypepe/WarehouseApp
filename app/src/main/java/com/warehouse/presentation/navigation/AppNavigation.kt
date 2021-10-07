package com.warehouse.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.warehouse.domain.ContactViewModel
import com.warehouse.domain.ExchangeViewModel
import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.presentation.screens.*
import com.warehouse.repository.model.Request

@Composable
fun AppNavigation(requestViewModel: RequestViewModel, contactViewModel: ContactViewModel,
                  exchangeViewModel: ExchangeViewModel, fullUri: Uri?) {
    Surface(color = MaterialTheme.colors.background) {
        val bottomItems = listOf("Requests", "Make request")
        val navController = rememberNavController()
        val uriPat = "www.warehouse_app.com"
        val context = LocalContext.current
//        val loading = exchangeViewModel.loading.observeAsState()
//        val price_ = exchangeViewModel.price.observeAsState()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    bottomItems.forEach { screen ->
                        BottomNavigationItem(selected = false,
                            onClick = { navController.navigate(screen) },
                            label = { Text(screen) }, icon = { })
                    }// Foreach
                } //BottomNavigation
            } // bottomBar
        ) { innerPadding ->
            NavHost(navController = navController, startDestination = "Requests") {
                composable("Requests") {
                    Box(modifier = Modifier.padding(innerPadding))
                    {
//                        loading.value?.let { it1 -> LoadProgress(it1) }
                        StartCardViewList(navController, requestViewModel) }
                }

                composable("Make request") { MakeRequestScreen(navController, requestViewModel) }

                composable("details") {
                    navController.previousBackStackEntry?.arguments?.getParcelable<Request>("REQUEST")
                        ?.let {
                            DetailScreen(request = it, navController)
                        }
                }// route detail

                composable("details/change/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType}))
                { backStackEntry ->
                    val arg = backStackEntry.arguments?.getString("id")
                    if ( arg != null)
                    {
                        PriceChooseScreen(navController, requestViewModel, exchangeViewModel, Integer.parseInt(arg))
                    }
                    else {
                        Text("Something went wrong")
                    }
                }

                composable("loading/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.StringType}))
                { backStackEntry ->
                    val arg = backStackEntry.arguments?.getString("id")
                    if ( arg != null) {
                        LoadingScreen(requestViewModel, exchangeViewModel, navController, Integer.parseInt(arg))
                    }
                    else {
                        Text("Something went wrong 112")
                    }
                }

                composable("contacts") {
                    ContactScreen(
                        navController = navController,
                        context as MainActivity, requestViewModel = requestViewModel,
                        contactViewModel = contactViewModel
                    )
                }

                composable(
                    "detail?id={id}",
                    deepLinks = listOf(navDeepLink { uriPattern = "$uriPat/detail?id={id}" })
                ) {
                    val tokens: List<String> = fullUri.toString().split("=")
                    val id: Int? = try {
                        Integer.parseInt(tokens[1])
                    } catch (e: Exception) {
                        null
                    }
                    DetailFromDeep(requestViewModel, id)
                }// Deep link handler
            }// NavHost
        }// Scaffold
    }// Surface
}