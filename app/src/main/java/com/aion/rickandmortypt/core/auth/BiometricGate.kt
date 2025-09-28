package com.aion.rickandmortypt.core.auth

import androidx.biometric.BiometricManager

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.launch

@Composable
fun BiometricGate(
    modifier: Modifier = Modifier.fillMaxSize(),
    onAuthenticatedContent: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    //val activity = remember(ctx) { ctx as FragmentActivity }
    val activity = ctx as FragmentActivity

    val authenticator = remember(activity) { BiometricAuth(activity) }

    var canAuth by remember { mutableStateOf(authenticator.canAuthenticate()) }
    var authOk by remember { mutableStateOf<Boolean?>(null) }
    var asking by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (canAuth == BiometricManager.BIOMETRIC_SUCCESS) {
            asking = true
            authOk = authenticator.authenticate()
            asking = false
        }
    }

    when {
        asking -> {
            Box(modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        authOk == true -> {
            onAuthenticatedContent()
        }
    }
}