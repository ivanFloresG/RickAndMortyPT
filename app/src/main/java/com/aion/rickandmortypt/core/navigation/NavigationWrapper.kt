package com.aion.rickandmortypt.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.aion.rickandmortypt.features.characterDetails.ui.CharacterDetailScreen
import com.aion.rickandmortypt.features.characterDetails.ui.CharacterViewModel
import com.aion.rickandmortypt.features.characterList.ui.CharacterListScreen
import com.aion.rickandmortypt.features.characterList.ui.CharacterListViewModel
import com.aion.rickandmortypt.ui.theme.RickAndMortyPTTheme

@Composable
fun NavigationWrapper(
    characterListViewModel: CharacterListViewModel,
    characterViewModel: CharacterViewModel
){

    RickAndMortyPTTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Principal) {
            composable<Principal> {
                CharacterListScreen(characterListViewModel, navController)
            }

            composable<Details> { backStackEntry ->
                val args: Details = backStackEntry.toRoute()
                CharacterDetailScreen(
                    characterViewModel,
                    id = args.id,
                ) { navController.popBackStack() }
            }


        }

    }
}