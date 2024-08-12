package com.devdmp.bikeexpert.presentation.home

import androidx.compose.runtime.Composable
import com.devdmp.bikeexpert.BakingScreen
import com.devdmp.bikeexpert.BakingViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(goToLogOut: () -> Unit, db: FirebaseFirestore, bakingViewModel: BakingViewModel) {
    BakingScreen(goToLogOut = goToLogOut, bakingViewModel = bakingViewModel, db = db)
}