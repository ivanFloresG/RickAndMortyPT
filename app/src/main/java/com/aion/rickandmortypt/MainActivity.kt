package com.aion.rickandmortypt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aion.rickandmortypt.core.navigation.NavigationWrapper
import com.aion.rickandmortypt.features.characterList.ui.CharacterListViewModel
import com.aion.rickandmortypt.ui.theme.RickAndMortyPTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val characterListViewModel: CharacterListViewModel by viewModels<CharacterListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWrapper(characterListViewModel)
        }
    }
}