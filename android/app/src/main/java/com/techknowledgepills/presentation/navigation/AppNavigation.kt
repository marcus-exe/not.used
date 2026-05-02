package com.techknowledgepills.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techknowledgepills.presentation.auth.LoginScreen
import com.techknowledgepills.presentation.auth.RegisterScreen
import com.techknowledgepills.presentation.content.ContentDetailScreen
import com.techknowledgepills.presentation.content.ContentListScreen
import com.techknowledgepills.presentation.home.HomeScreen
import com.techknowledgepills.presentation.recommendation.RecommendationScreen
import com.techknowledgepills.presentation.stress.StressIndicatorScreen

@Composable
fun AppNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                } },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) }
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                } },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToContentList = { navController.navigate(Screen.ContentList.route) },
                onNavigateToStress = { navController.navigate(Screen.StressIndicator.route) },
                onNavigateToRecommendations = { navController.navigate(Screen.Recommendations.route) },
                onNavigateToContentDetail = { id -> navController.navigate(Screen.ContentDetail.createRoute(id)) }
            )
        }
        composable(Screen.ContentList.route) {
            ContentListScreen(
                onNavigateToContentDetail = { id -> navController.navigate(Screen.ContentDetail.createRoute(id)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.ContentDetail.route) { backStackEntry ->
            val idString = backStackEntry.arguments?.getString("id") ?: "0"
            val id = idString.toIntOrNull() ?: 0
            ContentDetailScreen(
                contentId = id,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.StressIndicator.route) {
            StressIndicatorScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Recommendations.route) {
            RecommendationScreen(
                onNavigateToContentDetail = { id -> navController.navigate(Screen.ContentDetail.createRoute(id)) },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

