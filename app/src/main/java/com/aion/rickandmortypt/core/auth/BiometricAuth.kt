package com.aion.rickandmortypt.core.auth

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class BiometricAuth (
    private val activity: FragmentActivity,
    private val title: String = "Autenticación biométrica",
    private val subtitle: String = "Subtitle",
    private val description: String = "Description",
    private val negativeButtonText: String = "Cancelar"
) {
    fun canAuthenticate(): Int {
        val bm = BiometricManager.from(activity)
        return bm.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG or
                    BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
    }

    suspend fun authenticate(): Boolean = suspendCancellableCoroutine { cont ->
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText(negativeButtonText)
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG or
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
            )
            .build()

        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    if (!cont.isCompleted) cont.resume(true)
                }
                override fun onAuthenticationError(code: Int, errString: CharSequence) {
                    if (!cont.isCompleted) cont.resume(false)
                }
                override fun onAuthenticationFailed() {
                }
            })

        biometricPrompt.authenticate(promptInfo)

        cont.invokeOnCancellation {
        }
    }
}