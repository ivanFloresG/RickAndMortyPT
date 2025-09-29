package com.aion.rickandmortypt.features.characterList.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.aion.rickandmortypt.R
import com.aion.rickandmortypt.core.auth.BiometricAuthenticator
import com.aion.rickandmortypt.core.components.CharacterCardItem
import com.aion.rickandmortypt.core.navigation.Details
import com.aion.rickandmortypt.core.navigation.Favorites
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.Executor

@SuppressLint("ContextCastToActivity")
@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    navController: NavController
) {
    val listState = rememberLazyListState()

    val ui by viewModel.state.collectAsStateWithLifecycle()
    val snackBaHostState = remember { SnackbarHostState() }

    val biometricAuth = BiometricAuthenticator(LocalContext.current)

    LaunchedEffect(Unit) { viewModel.refreshPage() }

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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        snackbarHost = {
            SnackbarHost(snackBaHostState)
        },
        floatingActionButton = {
            val activity = LocalContext.current as FragmentActivity
            val title = LocalContext.current.getString(R.string.favorites)
            val subtitle = LocalContext.current.getString(R.string.favoritesDisclaimer)
            val cancel = LocalContext.current.getString(R.string.cancel)
            FloatingActionButton(onClick = {
                biometricAuth.promptBiometricAuth(
                    title = title,
                    subTitle = subtitle,
                    negativeButtonText = cancel,
                    fragmentActivity = activity,
                    onSucces = {
                        navController.navigate(Favorites)
                    },
                    onFailed = {
                    },
                    onError = { id, message ->
                    }
                )
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_favorite_fill),
                    contentDescription = null
                )
            }

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            SearchFields(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                ui,
                viewModel
            )

            if (ui.items.isEmpty() && !ui.isLoading && !ui.isRefreshing) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Sin resultados...")
                }
            } else {
                PullToRefreshBox(
                    isRefreshing = ui.isRefreshing,
                    onRefresh = { viewModel.refreshPage() },
                    modifier = Modifier.weight(0.9f),
                ) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                    ) {
                        itemsIndexed(
                            items = ui.items
                        ) { index, character ->
                            //AnimatedVisibility(remember { MutableTransitionState(true) }) {
                            CharacterCardItem(character) { idCharacter ->
                                navController.navigate(Details(idCharacter))
                            }
                            // }
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
    }
}


@Composable
fun SearchFields(modifier: Modifier, ui: CharacterListUiState, viewModel: CharacterListViewModel) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .clickable(onClick = { viewModel.onSearchClicked(!ui.isSearching) }, indication = null, interactionSource = null), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(R.string.search),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(9f)
                )
                Icon(
                    painter = if (ui.isSearching) painterResource(R.drawable.ic_arrow_up) else painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
            if (ui.isSearching) {
                Spacer(modifier = Modifier.height(15.dp))
                SearchInputField(
                    value = ui.filterName,
                    onValueChange = { if (!ui.isLoading) viewModel.onFilterNameChanged(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholderText = stringResource(R.string.name),
                    leadingIconPainter = painterResource(R.drawable.ic_badge),
                    leadingIconContentDescription = null,
                    enabled = !ui.isLoading,
                    isLoading = ui.isLoading
                )
                Spacer(modifier = Modifier.height(5.dp))
                SearchInputField(
                    value = ui.filterSpice,
                    onValueChange = { if (!ui.isLoading) viewModel.onFilterSpice(it) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholderText = stringResource(R.string.specie),
                    leadingIconPainter = painterResource(R.drawable.ic_genetic),
                    leadingIconContentDescription = null,
                    enabled = !ui.isLoading,
                    isLoading = ui.isLoading
                )
                Spacer(modifier = Modifier.height(8.dp))
                FilterChip(viewModel, ui)
                Spacer(modifier = Modifier.height(20.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Button(
                        onClick = { viewModel.refreshPage() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onBackground,
                        ),
                        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter_off),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(R.string.clearFilters))
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Button(
                        onClick = { viewModel.onApplyFilters() },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = stringResource(R.string.applyFilters))
                    }

                }

            }
        }
    }
}


@Composable
fun SearchInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = stringResource(R.string.name),
    leadingIconPainter: Painter? = painterResource(R.drawable.ic_badge),
    leadingIconContentDescription: String? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = { if (!isLoading) onValueChange(it) },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !isLoading,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest
        ),
        leadingIcon = leadingIconPainter?.let {
            {
                Icon(
                    painter = it,
                    contentDescription = leadingIconContentDescription
                )
            }
        },
        placeholder = { Text(text = placeholderText) },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
    )
}

@Composable
fun FilterChip(viewModel: CharacterListViewModel, ui: CharacterListUiState) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 7.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_medical_info),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = stringResource(R.string.state))
    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        FilterChip(
            onClick = {
                viewModel.onFilterState("alive")
            },
            label = {
                Text(stringResource(R.string.alive))
            },
            selected = ui.selectedAlive,
            leadingIcon = if (ui.selectedAlive) {
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )
        FilterChip(
            onClick = {
                viewModel.onFilterState("dead")
            },
            label = {
                Text(stringResource(R.string.dead))
            },
            selected = ui.selectedDeath,
            leadingIcon = if (ui.selectedDeath) {
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )
        FilterChip(
            onClick = {
                viewModel.onFilterState("unknown")
            },
            label = {
                Text(stringResource(R.string.uknown))
            },
            selected = ui.selectedUnknown,
            leadingIcon = if (ui.selectedUnknown) {
                {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )

    }
}
