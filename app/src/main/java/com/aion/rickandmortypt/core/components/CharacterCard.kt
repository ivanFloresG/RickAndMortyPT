package com.aion.rickandmortypt.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aion.rickandmortypt.features.characterDetails.domain.models.Character
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CharacterCardItem(
    character: Character,
    onItemClick: (Int) -> Unit
) {
    Card(
        onClick = { onItemClick(character.id) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        shape = RoundedCornerShape(corner = CornerSize(20.dp)),
        border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            CharacterImage(character.image, modifier = Modifier.size(100.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.fillMaxWidth().padding(end = 5.dp)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = character.status,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = character.species,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharacterImage(url: String, modifier: Modifier) {
    GlideImage(
        model = url,
        contentDescription = "A description of the image",
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )


}