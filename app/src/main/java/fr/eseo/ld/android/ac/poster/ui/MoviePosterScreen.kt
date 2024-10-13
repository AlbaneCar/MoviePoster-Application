package fr.eseo.ld.android.ac.poster.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import fr.eseo.ld.android.ac.poster.R
import fr.eseo.ld.android.ac.poster.network.OmdbApiService
import fr.eseo.ld.android.ac.poster.repositories.OmdbRepository
import fr.eseo.ld.android.ac.poster.viewmodels.MovieViewModel
import fr.eseo.ld.android.ac.poster.viewmodels.MovieViewModelFactory


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MoviePosterScreenContent() {
    val viewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(
            OmdbRepository(OmdbApiService.MoviePosterImplementation.api)
        )
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                val layoutDirection = LocalLayoutDirection.current
                SimpleComposeAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        )
                        .background(MaterialTheme.colorScheme.primary)
                )
            },
            content = { PosterContent(viewModel) }
        )
    }
}

@Composable
private fun SimpleComposeAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PosterContent(viewModel: MovieViewModel) {
    val movie by viewModel.movie
    var movieTitle by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            if (movie != null) {
                Log.d("FILM", "not null" + movie?.Poster)
                Image(
                    painter = rememberImagePainter(
                        data = movie!!.Poster,
                        builder = {
                            placeholder(R.drawable.ic_launcher_foreground)
                            error(R.drawable.ic_launcher_foreground)
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Log.d("FILM", "null")
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.fetchMovie(movieTitle) },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = stringResource(id = R.string.search))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = movieTitle,
            onValueChange = { movieTitle = it },
            label = { Text(text = stringResource(id = R.string.movie_title)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}