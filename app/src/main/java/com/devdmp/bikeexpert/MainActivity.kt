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
import com.devdmp.bikeexpert.navigation.HomeScreenNav
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.navigation.WelcomeScreenNav
import com.devdmp.bikeexpert.presentation.home.navigation.home
import com.devdmp.bikeexpert.presentation.login.navigation.login
import com.devdmp.bikeexpert.presentation.onboarding.OnboardingViewModel
import com.devdmp.bikeexpert.presentation.onboarding.navigation.onboarding
import com.devdmp.bikeexpert.ui.theme.BikeExpertTheme
import com.devdmp.data.onboarding.dto.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val bakingViewModel: BakingViewModel by viewModels()
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private var startDestination: Any = LoginScreenNav
    private lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        prefs = Prefs(this)
        startDestination = if (currentUser != null && prefs.onboardingCompleted) {
            HomeScreenNav
        } else {
            if (currentUser != null) {
                WelcomeScreenNav
            } else {
                LoginScreenNav
            }
        }
        setContent {
            BikeExpertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(
                        navController = navigationController,
                        startDestination = startDestination
                    ) {
                        login(
                            navigationController = navigationController,
                            auth = auth,
                            prefs = prefs
                        )
                        onboarding(
                            navigationController = navigationController,
                            bakingViewModel = bakingViewModel,
                            onboardingViewModel = onboardingViewModel,
                            prefs = prefs
                        )
                        home(
                            navigationController = navigationController,
                            bakingViewModel = bakingViewModel,
                            onboardingViewModel = onboardingViewModel,
                            auth = auth
                        )
                    }
                }
            }
        }
    }
}