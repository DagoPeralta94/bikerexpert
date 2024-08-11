package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(
    goToHome: () -> Unit,
    goToBack: () -> Unit,
    onboardingViewModel: OnboardingViewModel
) {
    val cylinderCapacity by onboardingViewModel.selectedBrandModel.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val motosPorTipoYCilindrada = mapOf(
                "100-200cc" to mapOf(
                    "Ciudad" to listOf("Honda CB110", "Bajaj Boxer CT100", "Yamaha YBR 125"),
                    "Aventura" to listOf("Honda XR150L", "Yamaha XTZ 125", "Suzuki DR150"),
                    "Cross" to listOf("AKT TT 125", "Honda XR190L", "Yamaha XTZ 125"),
                    "Scooter" to listOf("Honda Dio", "Yamaha BWS", "Suzuki Address 110")
                ),
                "200-500cc" to mapOf(
                    "Ciudad" to listOf("Bajaj Pulsar 200NS", "Yamaha FZ25", "Suzuki Gixxer 250"),
                    "Aventura" to listOf(
                        "Kawasaki Versys 300",
                        "Royal Enfield Himalayan 411",
                        "Suzuki V-Strom 250"
                    ),
                    "Deportiva" to listOf("Kawasaki Ninja 300", "Yamaha R3", "Honda CBR300R"),
                    "Touring" to listOf(
                        "Benelli TRK 502",
                        "Kawasaki Versys 300",
                        "Suzuki V-Strom 250"
                    )
                ),
                "600-1200cc" to mapOf(
                    "Aventura" to listOf("BMW F850GS", "Kawasaki Versys 650", "Suzuki V-Strom 650"),
                    "Deportiva" to listOf(
                        "Kawasaki Ninja ZX-6R",
                        "Yamaha YZF-R6",
                        "Honda CBR600RR"
                    ),
                    "Touring" to listOf(
                        "BMW R1200GS",
                        "Kawasaki Versys 1000",
                        "Suzuki V-Strom 1050"
                    ),
                    "Naked" to listOf("Yamaha MT-09", "Kawasaki Z900", "Honda CB650R")
                )
            )
            Text(
                "Select your model and brand motorcycle",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            val brandsAvailable =
                motosPorTipoYCilindrada[cylinderCapacity.cylinderCapacity]?.get(cylinderCapacity.bikeType)
            brandsAvailable?.forEach { brand ->
                ItemBrandBike(
                    name = brand,
                    onItemSelected = { selectedItem, isShowDialog ->
                        onboardingViewModel.updateBrand(selectedItem)
                        showDialog = isShowDialog
                    })
            }
        }
        if (showDialog) {
            BasicAlertDialog(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                onDismissRequest = { showDialog = false },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "This is your bike, ${cylinderCapacity.brand}, with ${cylinderCapacity.cylinderCapacity} to ${cylinderCapacity.bikeType} are you sure?",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                modifier = Modifier.size(height = 42.dp, width = 100.dp),
                                onClick = {
                                    goToHome()
                                    onboardingViewModel.sendModel(cylinderCapacity)
                                }) {
                                Text(text = "Yes")
                            }
                            Button(
                                modifier = Modifier.size(height = 42.dp, width = 100.dp),
                                onClick = { showDialog = false }) {
                                Text(text = "No")
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ItemBrandBike(name: String, onItemSelected: (String, Boolean) -> Unit) {
    LazyColumn {
        item {
            Card(modifier = Modifier
                .size(150.dp)
                .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    onItemSelected(name, true)
                }) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = name,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}