package com.devdmp.bikeexpert.presentation.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.BakingViewModel
import com.devdmp.bikeexpert.navigation.FirstScreenNav
import com.devdmp.bikeexpert.navigation.SecondScreenNav
import com.devdmp.bikeexpert.navigation.ThirdScreenNav
import com.devdmp.bikeexpert.navigation.WelcomeScreenNav
import com.devdmp.bikeexpert.presentation.onboarding.InitialQuestionScreen
import com.devdmp.bikeexpert.presentation.onboarding.OnboardingViewModel
import com.devdmp.bikeexpert.presentation.onboarding.SecondScreen
import com.devdmp.bikeexpert.presentation.onboarding.ThirdScreen
import com.devdmp.bikeexpert.presentation.onboarding.WelcomeScreen

internal fun NavGraphBuilder.onboarding(
    navigationController: NavController,
    bakingViewModel: BakingViewModel,
    onboardingViewModel: OnboardingViewModel
) {
    composable<WelcomeScreenNav> {
        WelcomeScreen {
            navigationController.navigate(FirstScreenNav)
        }
    }
    composable<FirstScreenNav> {
        InitialQuestionScreen(
            goToBack = {
                navigationController.popBackStack()
            },
            navigateSecondScreen = {
                navigationController.navigate(SecondScreenNav)
            }, onboardingViewModel = onboardingViewModel
        )
    }
    composable<SecondScreenNav> {
        SecondScreen(
            goToBack = {
                navigationController.popBackStack()
            },
            goToThirdScreen = {
                navigationController.navigate(ThirdScreenNav)
            },
            onboardingViewModel = onboardingViewModel
        )
    }
    composable<ThirdScreenNav> {
        ThirdScreen(
            goToBack = {
                navigationController.popBackStack()
            },
            onboardingViewModel = onboardingViewModel
        )
    }
}