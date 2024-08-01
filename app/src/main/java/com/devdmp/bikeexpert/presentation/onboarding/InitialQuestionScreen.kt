package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.devdmp.bikeexpert.BakingViewModel

@Composable
fun InitialQuestionScreen(navigateSecondScreen: () -> Unit, bakingViewModel: BakingViewModel = hiltViewModel()) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navigateSecondScreen() }) {
            Text(text = "To second screen")
        }
    }
}