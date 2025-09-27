package com.aion.rickandmortypt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.aion.rickandmortypt.core.navigation.NavigationWrapper
import com.aion.rickandmortypt.features.characterDetails.ui.CharacterViewModel
import com.aion.rickandmortypt.features.characterList.ui.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val characterListViewModel: CharacterListViewModel by viewModels<CharacterListViewModel>()
    private val characterViewModel: CharacterViewModel by viewModels<CharacterViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWrapper(
                characterListViewModel,
                characterViewModel
            )
        }
    }
}