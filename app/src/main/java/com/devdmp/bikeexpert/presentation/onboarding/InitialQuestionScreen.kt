package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devdmp.bikeexpert.BakingViewModel

@Composable
fun InitialQuestionScreen(navigateSecondScreen: () -> Unit, bakingViewModel: BakingViewModel) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        val listModelBiker = listOf(
            BikeType("Bike", "A bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.", true),
            BikeType("Mountain Bike", "A mountain bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Road Bike", "A road bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("E-Bike", "An e-bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Cruiser", "A cruiser is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Electric Scooter", "An electric scooter is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Electric Motorcycle", "An electric motorcycle is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Motorcycle", "A motorcycle is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false),
            BikeType("Scooter", "A scooter is a two-wheeled motor vehicle that is powered by an internal combustion engine.", false)
        )
        val selectedBikeTypes = remember { mutableStateListOf<BikeType>() }

        Column {
            listModelBiker.forEach { item ->
                ItemSelection(item = item, onItemSelected = { selectedItem ->
                    if (!selectedBikeTypes.contains(selectedItem)) {
                        selectedBikeTypes.add(selectedItem)
                    }
                })
            }
        }

        Button(onClick = {
            bakingViewModel.updateBikeTypes(selectedBikeTypes)
            navigateSecondScreen()
        }) {
            Text(text = "To second screen")
        }
    }
}

@Composable
fun ItemSelection(item: BikeType, onItemSelected: (BikeType) -> Unit) {
    DropdownMenuItem(
        onClick = {
            onItemSelected(item)
        },
        text = {
            Text(text = item.name)
        }
    )
}

data class BikeType(val name: String, val description: String, val isSelected: Boolean)