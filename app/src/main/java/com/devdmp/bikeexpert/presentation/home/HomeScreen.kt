package com.devdmp.bikeexpert.presentation.home

import androidx.compose.runtime.Composable
import com.devdmp.bikeexpert.BakingScreen

@Composable
fun HomeScreen(goToLogOut: () -> Unit) {
    BakingScreen(goToLogOut = goToLogOut)
}