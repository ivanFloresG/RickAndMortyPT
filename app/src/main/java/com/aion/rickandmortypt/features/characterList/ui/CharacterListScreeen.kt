package com.aion.rickandmortypt.features.characterList.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Text(text = "Android", modifier = Modifier.padding(innerPadding))
    }
}