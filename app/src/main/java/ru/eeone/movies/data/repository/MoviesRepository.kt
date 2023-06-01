package ru.eeone.movies.data.repository

import ru.eeone.movies.data.service.MoviesService
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesService: MoviesService) {
    suspend fun getPopularMovies() = moviesService.getPopular(API_KEY)

    suspend fun getTop250Movies() = moviesService.getTop250Movies(API_KEY)

    companion object {
        private const val API_KEY = "k_ucdw2k0h"
    }
}