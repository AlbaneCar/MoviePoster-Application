package fr.eseo.ld.android.ac.poster.repositories

import fr.eseo.ld.android.ac.poster.model.Movie
import fr.eseo.ld.android.ac.poster.network.OmdbApiService


class OmdbRepository(private val apiService: OmdbApiService) {
    suspend fun getMovie(title: String, apiKey: String): Movie? {
        return apiService.getMovie(title, apiKey)
    }
}