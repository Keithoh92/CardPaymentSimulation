package com.example.salestestapp.ui.shimmer.navigation

import android.graphics.Bitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType.Companion.BoolType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.salestestapp.ui.shimmer.CardPaymentScreenMain
import com.example.salestestapp.ui.shimmer.viewmodel.CardPaymentScreenViewModel

const val cardPaymentRoute = "card_payment_route"
const val cardPaymentArgs = "card_payment_args"

fun NavController.navigateToCardPaymentScreen(
    isSignature: Boolean,
    navOptions: NavOptions? = null
) {
    this.navigate("$cardPaymentRoute/${isSignature}", navOptions)
}

fun NavGraphBuilder.cardPaymentScreen(
    onBack: () -> Unit,
    onAcceptSignature: (bitmap: Bitmap) -> Unit,
) {
    composable(
        route = "$cardPaymentRoute/{$cardPaymentArgs}",
        arguments = listOf(navArgument(cardPaymentArgs) { type = BoolType })
    ) { navBackStackEntry ->
        val viewModel = hiltViewModel<CardPaymentScreenViewModel>()
        val isSignature = navBackStackEntry.arguments?.getBoolean(cardPaymentArgs)
        CardPaymentScreenMain(
            viewModel = viewModel,
            isSignature = isSignature ?: false,
            onBack = onBack,
            onAcceptSignature = onAcceptSignature
        )
    }
}