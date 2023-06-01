package ru.eeone.movies.presentation.utils

import ru.eeone.movies.data.model.Movie

sealed class ResponseWrapper(val movie: List<Movie>? = null, val errorMessage: String? = null) {
    object Loading : ResponseWrapper(null, null)
    class Success(movie: List<Movie>) : ResponseWrapper(movie, null)
    class Error(errorMessage: String) : ResponseWrapper(null, errorMessage)
}