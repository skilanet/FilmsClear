package com.practicum.movieexample.ui.movies

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.util.Creator
import com.practicum.movieexample.ui.poster.PosterActivity
import com.practicum.movieexample.R
import com.practicum.movieexample.domain.models.Movie
import com.practicum.movieexample.presentation.movies.MoviesSearchPresenter
import com.practicum.movieexample.presentation.movies.MoviesView
import com.practicum.movieexample.ui.movies.model.MoviesState

class MoviesActivity : AppCompatActivity(), MoviesView {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private lateinit var queryInput: EditText
    private lateinit var placeholderMessage: TextView
    private lateinit var moviesList: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val movieAdapter = MovieAdapter {
        if (clickDebounce()) {
            val intent = Intent(this, PosterActivity::class.java)
            intent.putExtra("poster", it.image)
            startActivity(intent)
        }
    }

    private var textWatcher: TextWatcher? = null

    private var isClickAllowed = true

    private val mainHandler = Handler(Looper.getMainLooper())

    private var moviesSearchPresenter: MoviesSearchPresenter? = null

    override fun onStart() {
        super.onStart()
        moviesSearchPresenter?.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        moviesSearchPresenter?.attachView(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesSearchPresenter = lastNonConfigurationInstance as? MoviesSearchPresenter
        if (moviesSearchPresenter == null) {
            moviesSearchPresenter = Creator.provideMoviesSearchController(this.applicationContext)
        }
        placeholderMessage = findViewById(R.id.errorMessage)
        queryInput = findViewById(R.id.search)
        moviesList = findViewById(R.id.movie_list)
        progressBar = findViewById(R.id.progressBar)

        textWatcher = object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                moviesSearchPresenter?.searchDebounce(s?.toString()?:"")
            }

            override fun afterTextChanged(s: Editable?) {}

        }

        textWatcher.let { queryInput.addTextChangedListener(it) }

        moviesList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        moviesList.adapter = movieAdapter
    }

    override fun onPause() {
        super.onPause()
        moviesSearchPresenter?.detachView()
    }

    override fun onStop() {
        super.onStop()
        moviesSearchPresenter?.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        moviesSearchPresenter?.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        moviesSearchPresenter?.detachView()
        textWatcher?.let { queryInput.removeTextChangedListener(it) }
        moviesSearchPresenter?.onDestroy()

        if (isFinishing) {
            moviesSearchPresenter = null
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            mainHandler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun showLoading() {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    private fun showEmpty(errorMessage: String) {
        moviesList.visibility = View.GONE
        placeholderMessage.visibility = View.VISIBLE
        progressBar.visibility = View.GONE

        placeholderMessage.text = errorMessage
    }

    @SuppressLint("NotifyDataSetChanged")
    fun showContent(movies: List<Movie>) {
        moviesList.visibility = View.VISIBLE
        placeholderMessage.visibility = View.GONE
        progressBar.visibility = View.GONE

        movieAdapter.movies.clear()
        movieAdapter.movies.addAll(movies)
        movieAdapter.notifyDataSetChanged()
    }

    override fun render(state: MoviesState) {
        when(state){
            is MoviesState.Loading -> showLoading()
            is MoviesState.Content -> showContent(state.movies)
            is MoviesState.Empty -> showEmpty(state.message)
            is MoviesState.Error -> showError(state.errorMessage)
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onRetainCustomNonConfigurationInstance()",
        "androidx.appcompat.app.AppCompatActivity"
    )
    )
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return moviesSearchPresenter
    }

    override fun showToast(additionalMessage: String) {
        Toast.makeText(this, additionalMessage, Toast.LENGTH_SHORT).show()

    }
}