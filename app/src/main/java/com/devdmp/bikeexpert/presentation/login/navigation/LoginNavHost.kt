package com.devdmp.bikeexpert.presentation.login.navigation

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.navigation.HomeScreenNav
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.navigation.WelcomeScreenNav
import com.devdmp.bikeexpert.presentation.login.LoginScreen
import com.devdmp.data.onboarding.dto.Prefs
import com.google.firebase.auth.FirebaseAuth

internal fun NavGraphBuilder.login(
    navigationController: NavController,
    auth: FirebaseAuth,
    prefs: Prefs,
    context: Context
) {
    composable<LoginScreenNav> {
        LoginScreen(goToLogin = {
            if (prefs.onboardingCompleted) {
                navigationController.navigate(HomeScreenNav)
            } else {
                navigationController.navigate(WelcomeScreenNav)
            }
        }, auth = auth,
            goToLoginFree = {
                auth.signInAnonymously().addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (prefs.onboardingCompleted) {
                            navigationController.navigate(HomeScreenNav)
                        } else {
                            navigationController.navigate(WelcomeScreenNav)
                        }
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}