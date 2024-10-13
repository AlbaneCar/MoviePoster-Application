package fr.eseo.ld.android.ac.poster.network

import fr.eseo.ld.android.ac.poster.model.Movie
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApiService {
    @GET("/")
    suspend fun getMovie(
        @Query("t") title: String,
        @Query("apikey") apikey: String
    ): Movie

    object MoviePosterImplementation {
        private const val BASE_URL = "https://www.omdbapi.com/"
        val api: OmdbApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OmdbApiService::class.java)
        }
    }

}