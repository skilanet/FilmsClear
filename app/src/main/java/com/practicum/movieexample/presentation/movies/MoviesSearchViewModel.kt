package com.practicum.movieexample.presentation.movies

import android.annotation.SuppressLint
import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.ui.movies.model.MoviesState
import com.practicum.movieexample.ui.movies.model.SingleLiveEvent

class MoviesSearchViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer { MoviesSearchViewModel(this[APPLICATION_KEY] as Application) }
        }

    }

    private val moviesInteractor = Creator.provideMoviesInteractor(getApplication())
    private val stateLiveData = MutableLiveData<MoviesState>()
    fun observeState(): LiveData<MoviesState> = stateLiveData

    private val showToast = SingleLiveEvent<String>()
    fun observeShowToast(): LiveData<String> = showToast
    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchText == changedText) {
            return
        }
        lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                @SuppressLint("NotifyDataSetChanged")
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    val movies = mutableListOf<Movie>()
                    if (foundMovies != null) {
                        movies.addAll(foundMovies)
                    }

                    when {
                        errorMessage != null -> {
                            renderState(MoviesState.Error("Something went wrong"))
                            showToast.postValue(errorMessage)
                        }

                        movies.isEmpty() -> renderState(MoviesState.Empty("Nothing found"))
                        else -> renderState(MoviesState.Content(movies))
                    }
                }
            })
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
    }

    fun renderState(state: MoviesState) {
        stateLiveData.postValue(state)
    }
}