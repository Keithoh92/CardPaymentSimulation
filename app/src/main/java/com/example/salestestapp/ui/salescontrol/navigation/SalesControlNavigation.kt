package com.example.salestestapp.ui.salescontrol.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.salestestapp.ui.salescontrol.SalesControlMain
import com.example.salestestapp.ui.salescontrol.viewmodel.SalesControlViewModel

const val salesControlRoute = "sales_control_route"

fun NavController.navigateToSalesControl(navOptions: NavOptions? = null) {
    this.navigate(salesControlRoute, navOptions)
}

fun NavGraphBuilder.salesControlScreen(onBack: () -> Unit) {
    composable(route = salesControlRoute) {
        val viewModel = hiltViewModel<SalesControlViewModel>()
        SalesControlMain(viewModel = viewModel, onBack = onBack)
    }
}