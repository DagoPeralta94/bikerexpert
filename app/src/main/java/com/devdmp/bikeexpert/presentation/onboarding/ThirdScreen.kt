package com.devdmp.bikeexpert.presentation.onboarding

import android.util.Log
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdmp.data.onboarding.dto.Prefs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThirdScreen(
    goToHome: () -> Unit,
    goToBack: () -> Unit,
    onboardingViewModel: OnboardingViewModel,
    db: FirebaseFirestore,
    prefs: Prefs
) {
    val cylinderCapacity by onboardingViewModel.selectedBrandModel.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
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
            ),
            title = {
                Text(
                    text = "Third selection",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val motorcyclesByTypeAndDisplacement = mapOf(
                "100-200cc" to mapOf(
                    "City" to listOf(
                        "Honda CB110", "Bajaj Boxer CT100", "Yamaha YBR 125", "Suzuki GN125",
                        "TVS Apache 160", "Hero Hunk 150", "AKT NKD 125", "Auteco Discover 125",
                        "Bajaj Pulsar 180", "Honda CB190R", "TVS Apache RTR 200"
                    ),
                    "Adventure" to listOf(
                        "Honda XR150L", "Yamaha XTZ 125", "Suzuki DR150", "AKT TTR 200",
                        "Kawasaki KLX 150", "Hero Xpulse 200", "TVS Apache 180", "Honda XR190L"
                    ),
                    "Cross" to listOf(
                        "AKT TT 125", "Honda XR190L", "Yamaha XTZ 125", "Kawasaki KLX 140",
                        "Suzuki DR200S", "Yamaha WR155R", "Kawasaki KLX 150", "Honda CRF150"
                    ),
                    "Scooter" to listOf(
                        "Honda Dio", "Yamaha BWS", "Suzuki Address 110", "TVS NTorq 125",
                        "Hero Maestro Edge 125", "Kymco Agility 150", "Honda Elite 125",
                        "Aprilia SR 150", "Benelli Zafferano 150"
                    ),
                    "Naked" to listOf(
                        "Kawasaki Z125", "Yamaha MT-15", "Honda CB150R", "Suzuki GSX-S150"
                    ),
                    "Enduro" to listOf(
                        "Yamaha WR155R", "Honda CRF150L", "Kawasaki KLX 150"
                    ),
                    "Chopper" to listOf(
                        "Bajaj Avenger 180", "Keeway Superlight 200", "UM Renegade Commando"
                    )
                ),
                "200-500cc" to mapOf(
                    "City" to listOf(
                        "Bajaj Pulsar 200NS",
                        "Yamaha FZ25",
                        "Suzuki Gixxer 250",
                        "Honda CB250 Twister",
                        "TVS Apache RTR 200",
                        "KTM Duke 200",
                        "Benelli TNT 25",
                        "Kawasaki Z250",
                        "Hero Xtreme 200S",
                        "Yamaha FZ-S FI",
                        "Suzuki GSX-S250"
                    ),
                    "Adventure" to listOf(
                        "Kawasaki Versys 300", "Royal Enfield Himalayan 411", "Suzuki V-Strom 250",
                        "BMW G310GS", "Honda CB500X", "KTM 390 Adventure", "Benelli TRK 251",
                        "Zontes T310", "CFMoto 400NK"
                    ),
                    "Sport" to listOf(
                        "Kawasaki Ninja 300", "Yamaha R3", "Honda CBR300R", "KTM RC 390",
                        "Suzuki GSX250R", "Benelli 302R", "TVS Apache RR310", "Kawasaki Ninja 400",
                        "BMW G310R", "CFMoto 300SR"
                    ),
                    "Touring" to listOf(
                        "Benelli TRK 502",
                        "Kawasaki Versys 300",
                        "Suzuki V-Strom 250",
                        "Honda CB500X",
                        "BMW G310GS",
                        "KTM 390 Adventure",
                        "Royal Enfield Himalayan",
                        "CFMoto 650MT",
                        "Zontes T310"
                    ),
                    "Naked" to listOf(
                        "Yamaha MT-03", "Kawasaki Z400", "Honda CB300R", "Suzuki GSX-S300"
                    ),
                    "Enduro" to listOf(
                        "KTM 350 EXC-F", "Yamaha WR250R", "Husqvarna FE 350"
                    ),
                    "Chopper" to listOf(
                        "UM Renegade Sport S", "Keeway Superlight 300", "Royal Enfield Classic 350"
                    )
                ),
                "600-1200cc" to mapOf(
                    "Adventure" to listOf(
                        "BMW F850GS",
                        "Kawasaki Versys 650",
                        "Suzuki V-Strom 650",
                        "Honda Africa Twin",
                        "Yamaha Tenere 700",
                        "Triumph Tiger 800",
                        "Ducati Multistrada 950",
                        "KTM 790 Adventure",
                        "Benelli TRK 502X"
                    ),
                    "Sport" to listOf(
                        "Kawasaki Ninja ZX-6R",
                        "Yamaha YZF-R6",
                        "Honda CBR600RR",
                        "Suzuki GSX-R750",
                        "Ducati Panigale V2",
                        "KTM RC 690",
                        "Triumph Daytona 765",
                        "BMW S1000RR",
                        "Aprilia RSV4",
                        "MV Agusta F3 800"
                    ),
                    "Touring" to listOf(
                        "BMW R1200GS",
                        "Kawasaki Versys 1000",
                        "Suzuki V-Strom 1050",
                        "Honda Goldwing",
                        "Ducati Multistrada 1260",
                        "Triumph Tiger 1200",
                        "Yamaha FJR1300",
                        "BMW K1600GT",
                        "KTM 1290 Super Adventure"
                    ),
                    "Naked" to listOf(
                        "Yamaha MT-09", "Kawasaki Z900", "Honda CB650R", "Suzuki GSX-S750",
                        "Triumph Street Triple", "Ducati Monster 821", "BMW F800R", "KTM Duke 790",
                        "Benelli TNT 600", "MV Agusta Brutale 800"
                    ),
                    "Enduro" to listOf(
                        "KTM 690 Enduro R", "Husqvarna 701 Enduro", "Yamaha WR450F"
                    ),
                    "Chopper" to listOf(
                        "Harley-Davidson Sportster 1200",
                        "Honda Shadow 750",
                        "Suzuki Boulevard M800"
                    )
                ),
                "1200cc+" to mapOf(
                    "Adventure" to listOf(
                        "BMW R1250GS", "Ducati Multistrada 1260", "KTM 1290 Super Adventure R",
                        "Triumph Tiger 1200", "Yamaha Super Tenere 1200", "Honda Africa Twin 1100"
                    ),
                    "Sport" to listOf(
                        "Ducati Panigale V4",
                        "BMW S1000RR",
                        "Kawasaki Ninja ZX-10R",
                        "Yamaha YZF-R1",
                        "Honda CBR1000RR",
                        "Suzuki GSX-R1000",
                        "Aprilia RSV4 1100",
                        "MV Agusta F4"
                    ),
                    "Touring" to listOf(
                        "Honda Goldwing", "BMW K1600GTL", "Yamaha FJR1300", "Kawasaki Concours 14",
                        "Ducati Multistrada V4", "Triumph Trophy", "Harley-Davidson Road Glide",
                        "Indian Chieftain", "Suzuki Hayabusa"
                    ),
                    "Naked" to listOf(
                        "Kawasaki Z H2",
                        "Yamaha MT-10",
                        "Ducati Streetfighter V4",
                        "BMW S1000R",
                        "KTM 1290 Super Duke R",
                        "Aprilia Tuono V4",
                        "Suzuki GSX-S1000",
                        "Triumph Speed Triple 1200",
                        "MV Agusta Brutale 1000",
                        "Harley-Davidson Fat Bob"
                    ),
                    "Enduro" to listOf(
                        "KTM 1290 Super Adventure R",
                        "Husqvarna 701 Enduro LR",
                        "BMW R1250GS Adventure"
                    ),
                    "Chopper" to listOf(
                        "Harley-Davidson Road King", "Indian Chief", "Honda Goldwing F6C"
                    )
                )
            )
            item {
                Text(
                    "Select your model and brand motorcycle",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                val brandsAvailable =
                    motorcyclesByTypeAndDisplacement[cylinderCapacity.cylinderCapacity]?.get(
                        cylinderCapacity.bikeType
                    )
                brandsAvailable?.forEach { brand ->
                    ItemBrandBike(
                        name = brand,
                        onItemSelected = { selectedItem, isShowDialog ->
                            onboardingViewModel.updateBrand(selectedItem)
                            showDialog = isShowDialog
                        })
                }
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
                            val userId = FirebaseAuth.getInstance().currentUser?.uid
                            val userPrefsBiker = hashMapOf(
                                "model" to cylinderCapacity.model,
                                "brand" to cylinderCapacity.brand,
                                "cylinderCapacity" to cylinderCapacity.cylinderCapacity,
                                "bikeType" to cylinderCapacity.bikeType
                            )
                            Button(
                                modifier = Modifier.size(height = 42.dp, width = 100.dp),
                                onClick = {
                                    if (userId != null) {
                                        db.collection("user_prefs_biker").document(userId)
                                            .set(userPrefsBiker).addOnSuccessListener {
                                                Log.d(
                                                    "Success",
                                                    "DocumentSnapshot successfully written!"
                                                )
                                                prefs.onboardingCompleted = true
                                                goToHome()
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w("Error", "Error writing document", e)
                                            }
                                    }
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
    Column {
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