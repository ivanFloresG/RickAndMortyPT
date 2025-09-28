package com.aion.rickandmortypt

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aion.rickandmortypt.core.navigation.NavigationWrapper
import com.aion.rickandmortypt.features.characterDetails.ui.CharacterViewModel
import com.aion.rickandmortypt.features.characterList.ui.CharacterListViewModel
import com.aion.rickandmortypt.features.favoriteList.ui.FavoriteListViewModel
import com.aion.rickandmortypt.features.map.ui.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val characterListViewModel: CharacterListViewModel by viewModels<CharacterListViewModel>()
    private val characterViewModel: CharacterViewModel by viewModels<CharacterViewModel>()
    private val mapViewModel: MapViewModel by viewModels<MapViewModel>()
    private val favoriteListViewModel: FavoriteListViewModel by viewModels<FavoriteListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationWrapper(
                characterListViewModel,
                characterViewModel,
                mapViewModel,
                favoriteListViewModel
            )
        }
    }
}