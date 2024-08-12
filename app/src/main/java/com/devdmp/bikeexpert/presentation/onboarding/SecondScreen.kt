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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ), title = {
                Text(
                    text = "Second selection",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            navigationIcon = {
                TextButton(onClick = { goToBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            })
        val motorcycleTypesByDisplacement = mapOf(
            "100-200cc" to listOf("City", "Adventure", "Cross", "Scooter"),
            "200-500cc" to listOf(
                "City",
                "Adventure",
                "Sport",
                "Touring",
                "Cruiser",
                "Naked",
                "Enduro"
            ),
            "600-1200cc" to listOf(
                "Adventure",
                "Sport",
                "Touring",
                "Naked",
                "Enduro"
            ),
            "1200cc+" to listOf("Adventure", "Sport", "Touring", "Naked", "Chopper")
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = "Select your bike type",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                when (cylinderCapacity.cylinderCapacity) {
                    "100-200cc" -> {
                        motorcycleTypesByDisplacement["100-200cc"]?.forEach { bikeType ->
                            ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                                onboardingViewModel.updateBikeType(selectedItem)
                                goToThirdScreen()
                            })
                        }
                    }

                    "200-500cc" -> {
                        motorcycleTypesByDisplacement["200-500cc"]?.forEach { bikeType ->
                            ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                                onboardingViewModel.updateBikeType(selectedItem)
                                goToThirdScreen()
                            })
                        }
                    }

                    "600-1200cc" -> {
                        motorcycleTypesByDisplacement["600-1200cc"]?.forEach { bikeType ->
                            ItemTypeBike(name = bikeType, onItemSelected = { selectedItem ->
                                onboardingViewModel.updateBikeType(selectedItem)
                                goToThirdScreen()
                            })
                        }
                    }

                    "1200cc+" -> {
                        motorcycleTypesByDisplacement["1200cc+"]?.forEach { bikeType ->
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
}

@Composable
fun ItemTypeBike(name: String, onItemSelected: (String) -> Unit) {
    Column {
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

