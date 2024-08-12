package com.devdmp.bikeexpert.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devdmp.bikeexpert.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    goToLogin: () -> Unit,
    goToLoginFree: () -> Unit = {},
    goToLoginGoogle: () -> Unit = {},
    goToLoginFacebook: () -> Unit = {},
    auth: FirebaseAuth
) {
    var isSignUp by remember { mutableStateOf(false) }
    var isRegister by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_login),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.27f))
        )
        if (isSignUp && !isRegister) {
            SignUpScreen(
                isSignUp = { isSignUp = it },
                goToLoginFree = goToLoginFree
            )
        } else {
            if (isRegister) {
                RegisterScreen(
                    goToLogin = goToLoginFree,
                    auth = auth,
                    isRegister = { isRegister = it })
            } else {
                SignInScreen(
                    goToLogin = goToLogin,
                    auth = auth,
                    isSignUp = { isSignUp = it },
                    isRegister = { isRegister = it })
            }
        }
    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(Color.Black.copy(alpha = 0.7f))
            .border(1.dp, Color.Black.copy(alpha = 0.4f), CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun SignUpScreen(
    goToLogin: () -> Unit = {},
    goToLoginFree: () -> Unit = {},
    goToLoginGoogle: () -> Unit = {},
    goToLoginFacebook: () -> Unit = {},
    isSignUp: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign up", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { goToLoginFree() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f))
        ) {
            Text(text = "Sign up free")
        }
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape),
            painter = painterResource(R.drawable.ic_google),
            title = "Sign up with Google",
            onClick = { goToLoginGoogle() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomButton(
            modifier = Modifier
                .fillMaxWidth()
                .clip(CircleShape),
            painter = painterResource(R.drawable.ic_facebook),
            title = "Sign up with Facebook",
            onClick = { goToLoginFacebook() }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { isSignUp(false) }
                .background(Color.Black.copy(alpha = 0.7f))
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = "Already have an account? Sign in",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun SignInScreen(
    goToLogin: () -> Unit,
    auth: FirebaseAuth,
    isSignUp: (Boolean) -> Unit,
    isRegister: (Boolean) -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign in", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            placeholder = { Text(text = "Email") },
            onValueChange = { email.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.White.copy(alpha = 0.8f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedContainerColor = Color.Black.copy(alpha = 0.6f),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password.value,
            placeholder = { Text(text = "Password") },
            onValueChange = { password.value = it },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.KeyboardArrowDown
                else Icons.Filled.KeyboardArrowUp

                val description =
                    if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = Color.White
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.White.copy(alpha = 0.8f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedContainerColor = Color.Black.copy(alpha = 0.6f),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val emailRight = validateEmail(email.value)
                val passwordRight = validatePassword(password.value)
                if (emailRight && passwordRight) {
                    auth.signInWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d("LoginScreen", "Success")
                                goToLogin()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error user does not exist",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Email or password is wrong", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f))
        ) {
            Text(text = "Login")
        }
        Button(
            onClick = { isSignUp(true) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f))
        ) {
            Text(text = "Sign up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { isRegister(true) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f))
        ) {
            Text(text = "Don't have an account? Register email")
        }
    }
}

@Composable
fun RegisterScreen(goToLogin: () -> Unit, auth: FirebaseAuth, isRegister: (Boolean) -> Unit) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Register", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            placeholder = { Text(text = "Email") },
            onValueChange = { email.value = it },
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusRequester.requestFocus()
                }
            ),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.White.copy(alpha = 0.8f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedContainerColor = Color.Black.copy(alpha = 0.6f),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password.value,
            placeholder = { Text(text = "Password") },
            onValueChange = { password.value = it },
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible)
                    Icons.Filled.KeyboardArrowDown
                else Icons.Filled.KeyboardArrowUp

                val description =
                    if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = Color.White
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedPlaceholderColor = Color.White.copy(alpha = 0.8f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.6f),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Color.Black,
                unfocusedContainerColor = Color.Black.copy(alpha = 0.4f),
                focusedContainerColor = Color.Black.copy(alpha = 0.6f),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val emailRight = validateEmail(email.value)
                val passwordRight = validatePassword(password.value)
                if (emailRight && passwordRight) {
                    auth.createUserWithEmailAndPassword(email.value, password.value)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d("LoginScreen", "Success")
                                goToLogin()
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Email format or password format is wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.7f))
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { isRegister(false) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black.copy(alpha = 0.6f))
        ) {
            Text(text = "Back to sign up")
        }
    }
}

fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validatePassword(password: String): Boolean {
    return password.length in 6..20
}