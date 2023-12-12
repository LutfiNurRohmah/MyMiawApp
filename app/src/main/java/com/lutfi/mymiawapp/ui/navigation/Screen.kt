package com.lutfi.mymiawapp.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite: Screen("favorite")
    object About: Screen("about")
    object DetailCat : Screen("home/{catId}") {
        fun createRoute(catId: Int) = "home/$catId"
    }
}