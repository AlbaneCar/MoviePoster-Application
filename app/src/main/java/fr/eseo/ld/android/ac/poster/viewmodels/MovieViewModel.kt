package fr.eseo.ld.android.ac.poster.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.eseo.ld.android.ac.poster.model.Movie
import fr.eseo.ld.android.ac.poster.repositories.OmdbRepository
import kotlinx.coroutines.launch


class MovieViewModel(private val repository: OmdbRepository) : ViewModel() {
    private val _movie = mutableStateOf<Movie?>(null)
    val movie: State<Movie?> = _movie

    fun fetchMovie(title : String) {
        viewModelScope.launch{
            try{
                val omdbMovie = repository.getMovie(title, "2927919f")
                Log.d("FILM","result "+omdbMovie)
                _movie.value = omdbMovie
            }
            catch(e : Exception) {
                Log.d("FILM","error ",e)
                _movie.value = null
            }
        }
    }
}

class MovieViewModelFactory(private val repository: OmdbRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Incorrect ViewModel")
    }
}