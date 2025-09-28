package com.aion.rickandmortypt.features.characterDetails.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aion.rickandmortypt.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun CharacterDetailScreen(
    viewModel: CharacterViewModel,
    id: Int,
    onBackPressed: () -> Unit,
) {
    val listState = rememberLazyListState()
    val ui by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) { viewModel.fetchCharacter(id) }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clearData()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "",
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
                actions = {
                    IconButton(
                        onClick = { viewModel.onFavoriteClicked() },
                        modifier = Modifier
                            .size(50.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerLow,
                                shape = RoundedCornerShape(50.dp)
                            )
                        ,
                        enabled = !ui.isLoading
                    ) {
                        Icon(
                            painter = if (ui.item.favorite) {
                                painterResource(R.drawable.ic_favorite_fill)
                            } else {
                                painterResource(R.drawable.ic_favorite)
                            },
                            contentDescription = "",
                            modifier = Modifier.size(25.dp),
                            tint = Color.Red
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {}
    ) { innerPadding ->
        if (ui.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ui.item.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                CharacterImage(ui.item.image, Modifier.size(180.dp))
                Spacer(modifier = Modifier.height(20.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Label(ui.item.gender, R.drawable.ic_medical_info)
                    Label(ui.item.species, R.drawable.ic_genetic)
                    Label(ui.item.status, R.drawable.ic_favorite_fill)
                }
                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Location(ui)
                Spacer(modifier = Modifier.height(10.dp))

            }
        }
    }

}

@Composable
fun Label(
    text: String,
    icon: Int
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Icon(
            painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(url: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
    ) {
        GlideImage(
            model = url,
            contentDescription = "A description of the image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }

}


@Composable
fun Location(uiState: CharacterUiState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(6.5f)) {
            Text(
                text = stringResource(R.string.locate),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surfaceContainerHighest
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = uiState.item.location.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
        }
        Button(onClick = {}, modifier = Modifier.weight(3.5f)) {
            Icon(
                painterResource(R.drawable.ic_location),
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .padding(end = 3.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(stringResource(R.string.locate))
        }
    }
}