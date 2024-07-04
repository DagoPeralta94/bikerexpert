package com.devdmp.bikeexpert.presentation.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.navigation.FirstScreenNav
import com.devdmp.bikeexpert.navigation.SecondScreenNav
import com.devdmp.bikeexpert.presentation.onboarding.InitialQuestionScreen
import com.devdmp.bikeexpert.presentation.onboarding.SecondScreen

internal fun NavGraphBuilder.onboarding(
    navigationController: NavController
) {
    composable<FirstScreenNav> {
        InitialQuestionScreen {
            navigationController.navigate(SecondScreenNav)
        }
    }
    composable<SecondScreenNav> {
        SecondScreen {
            navigationController.popBackStack()
        }
    }
}