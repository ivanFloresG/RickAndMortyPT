package com.aion.rickandmortypt.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aion.rickandmortypt.features.characterDetails.domain.models.Episode

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EpisodeCardItem(
    episode: Episode,
    onItemClick: (Int) -> Unit
) {
    Card(
        onClick = { onItemClick(episode.id) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (episode.watched) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            }
        ),
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = episode.id.toString(),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(6f)
            ) {
                Text(
                    text = episode.episode,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = episode.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = episode.airDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (episode.watched) {
                Text(text = "Visto")
            }
        }
    }
}