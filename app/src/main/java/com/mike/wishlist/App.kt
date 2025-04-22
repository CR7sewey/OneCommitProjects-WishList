package com.mike.wishlist

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Add : Screen("add")
    object Update : Screen("update/{id}") {
        fun createRoute(id: String) = "update/$id"
    }
}

@Composable
fun App(navHostController: NavHostController, changeBackArrow: (String) -> Unit, viewModelWish: MVVM_WishList, modifier: Modifier = Modifier) {

    var navGraph = navHostController.createGraph(
        startDestination = "home"
    ) {
        composable(Screen.Home.route) {
            HomeView(
                navHostController = navHostController,
                changeBackArrow = {
                    changeBackArrow.invoke("Edit Wish")
                },
                viewModelWish = viewModelWish,
                modifier = modifier
            )
        }
        composable(Screen.Add.route) {
            AddOne(
                viewModelWish = viewModelWish,
                navHostController = navHostController,
                changeBackArrow = {
                    changeBackArrow.invoke("Wish List")
                },
                modifier = modifier
            )
        }
        composable(Screen.Update.route, arguments = listOf(navArgument("id") { type =
            NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") // not needed
            val item = navHostController.previousBackStackEntry?.savedStateHandle?.get<WishList>("item")
            AddOne(
                item = item,
                viewModelWish = viewModelWish,
                navHostController = navHostController,
                changeBackArrow = {
                    changeBackArrow.invoke("Wish List")
                },
                modifier = modifier
            )
            // DetailsView(id)
        }
    }
    NavHost(
            navController = navHostController,
            graph = navGraph,
            modifier = modifier.padding(top = 112.dp)
        )


}