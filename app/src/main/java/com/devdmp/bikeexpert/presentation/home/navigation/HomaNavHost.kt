package com.devdmp.bikeexpert.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.BakingViewModel
import com.devdmp.bikeexpert.navigation.HomeScreenNav
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.presentation.home.HomeScreen
import com.devdmp.bikeexpert.presentation.onboarding.OnboardingViewModel
import com.google.firebase.auth.FirebaseAuth

internal fun NavGraphBuilder.home(
    navigationController: NavController,
    bakingViewModel: BakingViewModel,
    onboardingViewModel: OnboardingViewModel,
    auth: FirebaseAuth
) {
    composable<HomeScreenNav> {
        HomeScreen(goToLogOut = {
            navigationController.navigate(LoginScreenNav)
            auth.signOut()
        })
    }
}