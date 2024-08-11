package com.devdmp.bikeexpert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.presentation.home.navigation.home
import com.devdmp.bikeexpert.presentation.login.navigation.login
import com.devdmp.bikeexpert.presentation.onboarding.OnboardingViewModel
import com.devdmp.bikeexpert.presentation.onboarding.navigation.onboarding
import com.devdmp.bikeexpert.ui.theme.BikeExpertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bakingViewModel: BakingViewModel by viewModels()
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BikeExpertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = LoginScreenNav
                    ) {
                        login(
                            navigationController = navigationController
                        )
                        onboarding(
                            navigationController = navigationController,
                            bakingViewModel = bakingViewModel,
                            onboardingViewModel = onboardingViewModel
                        )
                        home(
                            navigationController = navigationController,
                            bakingViewModel = bakingViewModel,
                            onboardingViewModel = onboardingViewModel
                        )
                    }
                }
            }
        }
    }
}