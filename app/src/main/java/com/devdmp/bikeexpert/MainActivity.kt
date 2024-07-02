package com.devdmp.bikeexpert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devdmp.bikeexpert.presentation.initialform.InitialQuestionScreen
import com.devdmp.bikeexpert.presentation.initialform.SecondScreen
import com.devdmp.bikeexpert.presentation.initialform.navigation.FirstScreenNav
import com.devdmp.bikeexpert.presentation.initialform.navigation.SecondScreenNav
import com.devdmp.bikeexpert.ui.theme.BikeExpertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BikeExpertTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navigationController = rememberNavController()
                    NavHost(navController = navigationController, startDestination = FirstScreenNav) {
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
                }
            }
        }
    }
}