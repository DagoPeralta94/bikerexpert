package com.devdmp.bikeexpert.presentation.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdmp.bikeexpert.BakingViewModel

@Composable
fun InitialQuestionScreen(
    navigateSecondScreen: () -> Unit,
    bakingViewModel: BakingViewModel,
    onboardingViewModel: OnboardingViewModel
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        val optionsCylinderCapacity = listOf("100-200cc", "200-500cc", "600-1200cc")
        /* val listModelBiker = listOf(
            BikeType(
                "Bike",
                "A bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                true
            ),
            BikeType(
                "Mountain Bike",
                "A mountain bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Road Bike",
                "A road bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "E-Bike",
                "An e-bike is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Cruiser",
                "A cruiser is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Electric Scooter",
                "An electric scooter is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Electric Motorcycle",
                "An electric motorcycle is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Motorcycle",
                "A motorcycle is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            ),
            BikeType(
                "Scooter",
                "A scooter is a two-wheeled motor vehicle that is powered by an internal combustion engine.",
                false
            )
        )*/
        val selectedBikeTypes = remember { mutableStateListOf<BikeType>() }

        Column {
            optionsCylinderCapacity.forEach { item ->
                ItemCylinderCapacity(name = item, onItemSelected = { selectedItem ->
                    onboardingViewModel.updateCylinderCapacity(selectedItem)
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

@Composable fun ItemCylinderCapacity(name: String, onItemSelected: (String) -> Unit) {
    LazyColumn {
        item {
            Card(modifier = Modifier.size(150.dp).padding(16.dp), onClick = {
                onItemSelected(name)
            }) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = name)
                }
            }
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




val tiposMotoPorCilindrada = mapOf(
    "100-200cc" to listOf("Ciudad", "Aventura", "Cross", "Scooter"),
    "200-500cc" to listOf("Ciudad", "Aventura", "Deportiva", "Touring"),
    "600-1200cc" to listOf("Aventura", "Deportiva", "Touring", "Naked")
)
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
        "Touring" to listOf("Benelli TRK 502", "Kawasaki Versys 300", "Suzuki V-Strom 250")
    ),
    "600-1200cc" to mapOf(
        "Aventura" to listOf("BMW F850GS", "Kawasaki Versys 650", "Suzuki V-Strom 650"),
        "Deportiva" to listOf("Kawasaki Ninja ZX-6R", "Yamaha YZF-R6", "Honda CBR600RR"),
        "Touring" to listOf("BMW R1200GS", "Kawasaki Versys 1000", "Suzuki V-Strom 1050"),
        "Naked" to listOf("Yamaha MT-09", "Kawasaki Z900", "Honda CB650R")
    )
)

