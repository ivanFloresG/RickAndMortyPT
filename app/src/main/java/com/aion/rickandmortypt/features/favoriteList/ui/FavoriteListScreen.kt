package com.aion.rickandmortypt.features.favoriteList.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aion.rickandmortypt.R
import com.aion.rickandmortypt.core.auth.BiometricGate
import com.aion.rickandmortypt.core.components.CharacterCardItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteListViewModel,
    onBackPressed: () -> Unit
) {

    val listState = rememberLazyListState()
    val ui by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.onRefresh() }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPressed()
                        },
                        modifier = Modifier
                            .size(50.dp)
                    ) {
                        Icon(
                            painterResource(R.drawable.ic_back),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    )
    { innerPadding ->
        BiometricGate(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
            ) {
                itemsIndexed(
                    items = ui.items
                ) { index, character ->
                    AnimatedVisibility(remember { MutableTransitionState(true) }) {
                        CharacterCardItem(character) { idCharacter ->
                            //navController.navigate(Details(idCharacter))
                        }
                    }

                    if (index == (ui.page * 20) - 1) {
                        LaunchedEffect(Unit) {
                            viewModel.loadMore()
                        }
                    }
                }

                if (ui.isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}
/*
private var canAuthenticate = false
private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo

private fun setupAuth(){
    if (BiometricManager.from(LocalContext.current).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG
    or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS)) {
        canAuthenticate = true
        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Auth")
            .setSubtitle("Autenticate utilizando el sensor")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG
                    or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

    }
}

private fun authenticate(auth: (auth: Boolean) -> Unit){
    if(canAuthenticate){
        BiometricPrompt(
            this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    auth(true)
                }
            }).authenticate(promptInfo)
    } else {
        auth(false)
    }
}
 */