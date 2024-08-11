package com.devdmp.bikeexpert.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.BakingViewModel
import com.devdmp.bikeexpert.navigation.HomeScreenNav
import com.devdmp.bikeexpert.presentation.home.HomeScreen
import com.devdmp.bikeexpert.presentation.onboarding.OnboardingViewModel

internal fun NavGraphBuilder.home(
    navigationController: NavController,
    bakingViewModel: BakingViewModel,
    onboardingViewModel: OnboardingViewModel
) {
    composable<HomeScreenNav> {
        HomeScreen()
    }
}