package com.devdmp.bikeexpert.presentation.onboarding

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialQuestionScreen(
    goToBack: () -> Unit,
    navigateSecondScreen: () -> Unit,
    onboardingViewModel: OnboardingViewModel
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        val optionsCylinderCapacity = listOf("100-200cc", "200-500cc", "600-1200cc")
        Column {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "First selection",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
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
                Text(
                    text = "Select your cylinder capacity to motorcycle",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                optionsCylinderCapacity.forEach { item ->
                    ItemCylinderCapacity(name = item, onItemSelected = { selectedItem ->
                        onboardingViewModel.updateCylinderCapacity(selectedItem)
                        navigateSecondScreen()
                    })
                }
            }
        }
    }
}

@Composable
fun ItemCylinderCapacity(name: String, onItemSelected: (String) -> Unit) {
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

data class BikeType(val name: String, val description: String, val isSelected: Boolean)

data class CylinderCapacity(
    val range: String
)

data class BikeTypes(
    val cylinderCapacity: CylinderCapacity,
    val type: String
)

data class BrandModel(
    val brandModel: BikeTypes,
    val brand: String,
    val model: String
)

