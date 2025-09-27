package com.aion.rickandmortypt.features.characterList.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aion.rickandmortypt.core.CharacterCardItem
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel) {
    val listState = rememberLazyListState()

    val ui by viewModel.state.collectAsStateWithLifecycle()
    val snackBaHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { viewModel.onRefresh() }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is CharacterListViewModelUiEvent.ShowSnackBar ->
                    snackBaHostState.showSnackbar(event.message)

                CharacterListViewModelUiEvent.ScrollToTop ->
                    listState.scrollToItem(0)

                is CharacterListViewModelUiEvent.NavigateToDetail -> TODO()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        snackbarHost = {
            SnackbarHost(snackBaHostState)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (ui.items.isEmpty() && !ui.isLoading && !ui.isRefreshing) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Sin resultados...")
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(0.9f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                ) {
                    itemsIndexed(
                        items = ui.items
                    ) { index, character ->
                        AnimatedVisibility(remember { MutableTransitionState(true) }) {
                            CharacterCardItem(character) {
                                 idCharacter -> viewModel.onItemClicked(idCharacter)
                            }
                        }
                        if (index == (ui.page * 20) -1) {
                            LaunchedEffect(Unit) {
                                viewModel.loadMore()
                            }
                        }
                    }

                    if (ui.isRefreshing) {
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
}