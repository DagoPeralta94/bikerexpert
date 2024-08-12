package com.devdmp.bikeexpert.presentation.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.navigation.WelcomeScreenNav
import com.devdmp.bikeexpert.presentation.login.LoginScreen
import com.google.firebase.auth.FirebaseAuth

internal fun NavGraphBuilder.login(
    navigationController: NavController,
    auth: FirebaseAuth
) {
    composable<LoginScreenNav> {
        LoginScreen(goToLogin = {
            navigationController.navigate(WelcomeScreenNav)
        }, auth = auth)
    }
}