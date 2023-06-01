package ru.eeone.movies.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import ru.eeone.movies.data.model.GetMoviesResponse

interface MoviesService {
    @GET("/API/MostPopularMovies/{apiKey}")
    suspend fun getPopular(@Path("apiKey") apiKey: String): GetMoviesResponse

    @GET("/API/Top250Movies/{apiKey}")
    suspend fun getTop250Movies(@Path("apiKey") apiKey: String): GetMoviesResponse
}