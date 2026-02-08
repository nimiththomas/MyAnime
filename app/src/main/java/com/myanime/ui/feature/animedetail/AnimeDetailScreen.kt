package com.myanime.ui.feature.animedetail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.myanime.R
import com.myanime.ui.components.FullScreenLoader
import com.myanime.ui.components.GenreBadge
import com.myanime.ui.components.RatingBadge
import com.myanime.ui.feature.animedetail.models.AnimeDetailUiState
import com.myanime.ui.theme.MyAnimeBackground
import com.myanime.ui.theme.TextColor
import com.myanime.ui.theme.WhiteColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AnimeDetailScreen(
    viewModel: AnimeDetailViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MyAnimeBackground,
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is AnimeDetailUiState.Loading -> {
                    FullScreenLoader()
                }

                is AnimeDetailUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is AnimeDetailUiState.Success -> {
                    val anime = state.anime
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                            ) {
                                AsyncImage(
                                    model = anime.imageLarge,
                                    contentDescription = anime.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                if (!anime.trailerUrl.isNullOrBlank()) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        Color.Black
                                                    ),
                                                    startY = 150f
                                                )
                                            )
                                            .clickable {
                                                try {
                                                    val intent = Intent(
                                                        Intent.ACTION_VIEW,
                                                        anime.trailerUrl.toUri()
                                                    )
                                                    context.startActivity(intent)
                                                } catch (e: Exception) {
                                                    e.printStackTrace()
                                                }
                                            }
                                    )

                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = "Play Trailer",
                                        tint = WhiteColor,
                                        modifier = Modifier
                                            .size(70.dp)
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        item {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = anime.title,
                                    style = MaterialTheme.typography.headlineLarge,
                                    color = TextColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    anime.genres.split(",").forEach { genre ->
                                        GenreBadge(genre = genre.trim())
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = stringResource(
                                        R.string.episodes_no,
                                        anime.episodes ?: stringResource(R.string.n_a)
                                    ),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextColor
                                )
                                anime.rating?.let {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    RatingBadge(rating = it)
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.synopsis),
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = TextColor
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = anime.synopsis
                                        ?: stringResource(R.string.no_synopsis_available),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
