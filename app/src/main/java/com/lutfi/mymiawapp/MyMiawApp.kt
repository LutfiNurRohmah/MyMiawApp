package com.lutfi.mymiawapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lutfi.mymiawapp.ui.component.MyTopBar
import com.lutfi.mymiawapp.ui.component.MyTopBar2
import com.lutfi.mymiawapp.ui.navigation.Screen
import com.lutfi.mymiawapp.ui.screen.about.AboutScreen
import com.lutfi.mymiawapp.ui.screen.detail.DetailScreen
import com.lutfi.mymiawapp.ui.screen.favorite.FavoriteScreen
import com.lutfi.mymiawapp.ui.screen.home.HomeScreen

@Composable
fun MyMiawApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            when (currentRoute) {
                Screen.Home.route -> {
                    MyTopBar(navController)
                }
                Screen.Favorite.route -> {
                    MyTopBar2(navController = navController, text = "Favorite Miaw")
                }
                Screen.DetailCat.route -> {
                    MyTopBar2(navController = navController, text = "Detail Cat")
                }
                else -> {
                    MyTopBar2(navController = navController, text = "About")
                }
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { catId ->
                        navController.navigate(Screen.DetailCat.createRoute(catId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { catId ->
                        navController.navigate(Screen.DetailCat.createRoute(catId))
                    }
                )
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailCat.route,
                arguments = listOf(navArgument("catId") { type = NavType.IntType }),
            ) {
                val id = it.arguments?.getInt("catId") ?: -1
                DetailScreen(
                    catId = id
                )
            }
        }
    }

}