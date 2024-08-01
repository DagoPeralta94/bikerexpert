package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.devdmp.bikeexpert.BakingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(goToBack: () -> Unit, bakingViewModel: BakingViewModel) {
    val bikeTypes by bakingViewModel.bikeTypes.collectAsState()
    Column {
        CenterAlignedTopAppBar(title = {
            Text(text = "Second Screen")
        },
            navigationIcon = {
                TextButton(onClick = { goToBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            })
        Text("Bike Types:")
        bikeTypes.forEach { bikeType ->
            Text(bikeType.name)
        }
    }
}