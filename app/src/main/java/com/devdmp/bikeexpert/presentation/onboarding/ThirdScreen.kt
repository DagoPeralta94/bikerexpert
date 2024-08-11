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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(goToBack: () -> Unit, onboardingViewModel: OnboardingViewModel) {
    val cylinderCapacity by onboardingViewModel.selectedBrandModel.collectAsState()
    Column {
        CenterAlignedTopAppBar(title = {
            Text(text = "Third selection", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        Column {
            cylinderCapacity.cylinderCapacity?.let { cylinderCapacity ->
                Text(cylinderCapacity)
            }
            cylinderCapacity.bikeType?.let { bikeType ->
                Text(bikeType)
            }
        }
    }
}