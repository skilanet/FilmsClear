package com.practicum.movieexample.presentation.movies

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.ui.movies.model.MoviesState

class MoviesSearchPresenter(private val view: MoviesView, context: Context) {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val movies = ArrayList<Movie>()

    private val moviesInteractor = Creator.provideMoviesInteractor(context)
    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            view.render(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                @SuppressLint("NotifyDataSetChanged")
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                view.render(MoviesState.Error("Something went wrong"))
                                view.showToast(errorMessage)
                            }
                            movies.isEmpty() -> view.render(MoviesState.Empty("Nothing found"))
                            else -> view.render(MoviesState.Content(movies))
                        }
                    }
                }
            })
        }
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }
}