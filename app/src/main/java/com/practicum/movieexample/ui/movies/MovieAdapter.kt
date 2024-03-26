package com.practicum.movieexample.ui.movies

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.movieexample.domain.models.Movie

class MovieAdapter(private val clickListener: MovieClickListener): RecyclerView.Adapter<MovieViewHolder>() {
    var movies = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder = MovieViewHolder(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
        holder.itemView.setOnClickListener { clickListener.onMovieClick(movies.get(position)) }
    }

    override fun getItemCount(): Int = movies.size

    fun interface MovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}