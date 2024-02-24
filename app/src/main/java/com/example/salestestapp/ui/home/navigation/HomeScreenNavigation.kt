package com.example.salestestapp.ui.home.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.salestestapp.ui.home.HomeScreenMain
import com.example.salestestapp.ui.home.viewmodel.HomeScreenViewModel

const val homeScreenRoute = "home_route"

fun NavGraphBuilder.homeScreen(
    onCard: (isSignature: Boolean) -> Unit,
    onSalesControl: () -> Unit,
    onBack: () -> Unit,
) {
    composable(route = homeScreenRoute) {
        val viewModel = hiltViewModel<HomeScreenViewModel>()
        HomeScreenMain(
            viewModel = viewModel,
            onCard = onCard,
            onSalesControl = onSalesControl,
            onBack = onBack
        )
    }
}