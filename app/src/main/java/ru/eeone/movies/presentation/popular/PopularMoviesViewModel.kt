package ru.eeone.movies.presentation.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.eeone.movies.data.repository.MoviesRepository
import ru.eeone.movies.presentation.utils.ResponseWrapper
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {
    private val _moviesLiveData: MutableLiveData<ResponseWrapper> = MutableLiveData()
    val moviesLiveData: LiveData<ResponseWrapper> = _moviesLiveData

    suspend fun getPopularMovies() {
        _moviesLiveData.postValue(ResponseWrapper.Loading)
        try {
            val result = moviesRepository.getPopularMovies()
            if (result.errorMessage.isNotEmpty()) {
                _moviesLiveData.postValue(ResponseWrapper.Error(result.errorMessage))
            } else {
                _moviesLiveData.postValue(ResponseWrapper.Success(result.movies))
            }
        } catch (exception: Exception) {
            _moviesLiveData.postValue(
                ResponseWrapper.Error(
                    exception.message ?: "An unexpected error occurred"
                )
            )
        }
    }
}