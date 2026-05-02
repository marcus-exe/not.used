package com.techknowledgepills.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object ContentList : Screen("content_list")
    object ContentDetail : Screen("content_detail/{id}") {
        fun createRoute(id: Int): String = "content_detail/$id"
    }
    object StressIndicator : Screen("stress_indicator")
    object Recommendations : Screen("recommendations")
}

