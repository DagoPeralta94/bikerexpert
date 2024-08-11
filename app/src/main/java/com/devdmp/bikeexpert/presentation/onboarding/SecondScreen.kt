package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    goToThirdScreen: () -> Unit,
    goToBack: () -> Unit,
    onboardingViewModel: OnboardingViewModel
) {
    val cylinderCapacity by onboardingViewModel.selectedBrandModel.collectAsState()
    Column {
        CenterAlignedTopAppBar(title = {
            Text(text = "Second selection", fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
        val tiposMotoPorCilindrada = mapOf(
            "100-200cc" to listOf("Ciudad", "Aventura", "Cross", "Scooter"),
            "200-500cc" to listOf("Ciudad", "Aventura", "Deportiva", "Touring"),
            "600-1200cc" to listOf("Aventura", "Deportiva", "Touring", "Naked")
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Select your bike type",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            when (cylinderCapacity.cylinderCapacity) {
                "100-200cc" -> {
                    tiposMotoPorCilindrada["100-200cc"]?.forEach { bikeType ->
                        ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                            onboardingViewModel.updateBikeType(selectedItem)
                            goToThirdScreen()
                        })
                    }
                }

                "200-500cc" -> {
                    tiposMotoPorCilindrada["200-500cc"]?.forEach { bikeType ->
                        ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                            onboardingViewModel.updateBikeType(selectedItem)
                            goToThirdScreen()
                        })
                    }
                }

                "600-1200cc" -> {
                    tiposMotoPorCilindrada["600-1200cc"]?.forEach { bikeType ->
                        ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                            onboardingViewModel.updateBikeType(selectedItem)
                            goToThirdScreen()
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun ItemTypeBike(name: String, onItemSelected: (String) -> Unit) {
    LazyColumn {
        item {
            Card(modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                onItemSelected(name)
            }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = name)
                }
            }
        }
    }
}

