package com.devdmp.bikeexpert.presentation.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.devdmp.bikeexpert.navigation.HomeScreenNav
import com.devdmp.bikeexpert.navigation.LoginScreenNav
import com.devdmp.bikeexpert.presentation.home.HomeScreen
import com.devdmp.bikeexpert.presentation.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

internal fun NavGraphBuilder.home(
    navigationController: NavController,
    homeViewModel: HomeViewModel,
    auth: FirebaseAuth,
    db: FirebaseFirestore
) {
    composable<HomeScreenNav> {
        HomeScreen(goToLogOut = {
            navigationController.navigate(LoginScreenNav)
            auth.signOut()
        }, homeViewModel = homeViewModel, db = db)
    }
}