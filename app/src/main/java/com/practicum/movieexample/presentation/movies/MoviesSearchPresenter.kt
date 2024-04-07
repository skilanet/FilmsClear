package com.practicum.movieexample.presentation.movies

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.domain.api.MoviesInteractor
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.ui.movies.model.MoviesState

class MoviesSearchPresenter(context: Context) {
    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private var view: MoviesView? = null
    private var state: MoviesState? = null

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
                                view?.showToast(errorMessage)
                            }
                            movies.isEmpty() -> renderState(MoviesState.Empty("Nothing found"))
                            else -> renderState(MoviesState.Content(movies))
                        }
                    }
                }
            })
        }
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun attachView(view: MoviesView) {
        this.view = view
        state?.let { view.render(it) }
        Log.d("ATTACHVIEW", "this")
    }

    fun detachView() {
        this.view = null
    }

    fun renderState(state: MoviesState) {
        this.state = state
        this.view?.render(state)
    }
}