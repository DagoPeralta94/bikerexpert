package com.devdmp.bikeexpert.presentation.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdmp.bikeexpert.R
import com.devdmp.bikeexpert.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun HomeScreen(
    goToLogOut: () -> Unit,
    db: FirebaseFirestore,
    homeViewModel: HomeViewModel
) {
    BikeHomeScreen(
        goToLogOut = goToLogOut,
        homeViewModel = homeViewModel,
        db = db
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BikeHomeScreen(
    homeViewModel: HomeViewModel,
    goToLogOut: () -> Unit,
    db: FirebaseFirestore
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    LaunchedEffect(userId) {
        if (userId != null) {
            db.collection("user_prefs_biker").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val userPrefs = document.data
                        val model = userPrefs?.get("model") as String?
                        val brand = userPrefs?.get("brand") as String?
                        val cylinderCapacity = userPrefs?.get("cylinderCapacity") as String?
                        val bikeType = userPrefs?.get("bikeType") as String?
                        homeViewModel.updateUserPrefs(
                            model.orEmpty(),
                            brand.orEmpty(),
                            cylinderCapacity.orEmpty(),
                            bikeType.orEmpty()
                        )
                    } else {
                        Log.d("Error", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("Error", "Error getting document", e)
                }
        } else {
            Log.w("Error", "User ID is null")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.Black.copy(alpha = 0.7f)
                    )
                )
            )
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                Text(
                    text = stringResource(R.string.bike_home_title),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            },
            actions = {
                IconButton(onClick = { goToLogOut() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        )
        ChatScreen(homeViewModel = homeViewModel)
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun ChatScreen(homeViewModel: HomeViewModel) {
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }
    val uiState by homeViewModel.uiState.collectAsState()
    var firstQuestion by remember { mutableStateOf(true) }
    var result by rememberSaveable { mutableStateOf("") }
    var lastResult by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            reverseLayout = true
        ) {
            if (firstQuestion) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 128.dp),
                        textAlign = TextAlign.Center,
                        text = "ASK YOUR QUESTION...",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            } else {
                items(messages.size) { index ->
                    val msg =
                        messages[messages.size - 1 - index]
                    ChatBubble(message = msg)
                }
            }
        }
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text(text = "Type a message") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (message.isNotBlank()) {
                    homeViewModel.sendPrompt(message)
                    firstQuestion = false
                    messages.add(ChatMessage(message, isUser = true))
                    message = ""
                }
            }) {
                Text("Send")
            }
        }
        if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            if (uiState is UiState.Error) {
                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                result = (uiState as UiState.Success).outputText
                if (result != lastResult) {
                    messages.add(ChatMessage(result, isUser = false))
                    lastResult = result
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val backgroundColor =
        if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 8.dp),
            shape = MaterialTheme.shapes.medium,
            color = backgroundColor
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}