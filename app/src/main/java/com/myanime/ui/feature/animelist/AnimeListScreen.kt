package com.myanime.ui.feature.animelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.myanime.R
import com.myanime.ui.components.AnimeCard
import com.myanime.ui.components.FullScreenLoader
import com.myanime.ui.feature.animelist.models.Anime
import com.myanime.ui.feature.animelist.models.AnimeListUiState
import com.myanime.ui.theme.MyAnimeBackground
import com.myanime.ui.theme.TextColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    viewModel: AnimeListViewModel = hiltViewModel(),
    onAnimeClick: (id: Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MyAnimeBackground,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.myanime), color = TextColor) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MyAnimeBackground
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is AnimeListUiState.Loading -> {
                    FullScreenLoader()
                }

                is AnimeListUiState.Success -> {
                    val animePagingItems: LazyPagingItems<Anime> =
                        (uiState as AnimeListUiState.Success).animeFlow.collectAsLazyPagingItems()
                    var isRefreshed by rememberSaveable { mutableStateOf(false) }

                    val lifecycleOwner = LocalLifecycleOwner.current

                    LaunchedEffect(viewModel, lifecycleOwner) {
                        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            launch {
                                viewModel.isOnline.collect { online ->
                                    if (online) {
                                        if (!isRefreshed) {
                                            animePagingItems.refresh()
                                            isRefreshed = true
                                        }
                                    } else {
                                        isRefreshed = false
                                    }
                                }
                            }
                        }
                    }

                    if (animePagingItems.itemCount == 0) {
                        Box(Modifier.fillMaxSize()) {
                            Text(
                                text = stringResource(id = R.string.no_internet_no_data),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(18.dp)
                                    .fillMaxWidth(),
                                color = TextColor,
                                textAlign = TextAlign.Center
                            )
                        }

                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(animePagingItems.itemCount) { index ->
                                animePagingItems[index]?.let {
                                    AnimeCard(
                                        anime = it,
                                        onClick = { onAnimeClick(it.id) },
                                    )
                                }
                            }

                        }
                    }
                }

                is AnimeListUiState.Error -> {
                    Text(
                        text = (uiState as AnimeListUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}