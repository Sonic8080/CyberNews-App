package com.cachenews.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cachenews.domain.model.NewsCategory
import com.cachenews.ui.detail.DetailScreen
import com.cachenews.ui.feed.FeedScreen
import com.cachenews.ui.start.StartScreen
import com.cachenews.ui.settings.SettingsScreen

sealed class Screen(val route: String) {
    data object Start : Screen("start")
    data object Feed : Screen("feed/{category}") {
        fun createRoute(category: NewsCategory) = "feed/${category.name}"
    }
    data object Detail : Screen("detail/{articleId}") {
        fun createRoute(articleId: String) = "detail/$articleId"
    }
    data object Settings : Screen("settings")
}

@Composable
fun CacheNewsNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route,
        enterTransition = {
            slideInHorizontally(tween(300)) { it } + fadeIn(tween(300))
        },
        exitTransition = {
            slideOutHorizontally(tween(300)) { -it / 3 } + fadeOut(tween(200))
        },
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it / 3 } + fadeIn(tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(tween(300)) { it } + fadeOut(tween(200))
        }
    ) {
        composable(Screen.Start.route) {
            StartScreen(
                onCategorySelected = { category ->
                    navController.navigate(Screen.Feed.createRoute(category))
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        composable(
            route = Screen.Feed.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryStr = backStackEntry.arguments?.getString("category") ?: "CYBER"
            val category = try {
                NewsCategory.valueOf(categoryStr)
            } catch (_: Exception) {
                NewsCategory.CYBER
            }

            FeedScreen(
                category = category,
                onArticleClick = { articleId ->
                    navController.navigate(Screen.Detail.createRoute(articleId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("articleId") { type = NavType.StringType })
        ) {
            DetailScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
