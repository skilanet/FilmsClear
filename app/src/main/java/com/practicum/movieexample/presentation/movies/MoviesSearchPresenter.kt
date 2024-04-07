package com.practicum.movieexample.presentation.movies

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.ui.movies.model.MoviesState
import moxy.MvpPresenter

class MoviesSearchPresenter(context: Context) : MvpPresenter<MoviesView>() {
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
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                renderState(MoviesState.Error("Something went wrong"))
                                viewState.showToast(errorMessage)
                            }

                            movies.isEmpty() -> renderState(MoviesState.Empty("Nothing found"))
                            else -> renderState(MoviesState.Content(movies))
                        }
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun renderState(state: MoviesState) {
        viewState.render(state)
    }
}