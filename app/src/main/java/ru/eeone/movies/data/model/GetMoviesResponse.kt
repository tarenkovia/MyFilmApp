package ru.eeone.movies.data.model


import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("items")
    val movies: List<Movie>
)