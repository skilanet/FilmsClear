package com.practicum.movieexample.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.movieexample.R
import com.practicum.movieexample.domain.models.Movie

class MovieViewHolder(
    parent: ViewGroup,
    private val clickListener: MovieAdapter.MovieClickListener
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.movie_view, parent, false)
) {
    private var poster: ImageView = itemView.findViewById(R.id.poster)
    private var movieName: TextView = itemView.findViewById(R.id.movie_name)
    private var actors: TextView = itemView.findViewById(R.id.actors)

    fun bind(movie: Movie) {
        Glide.with(poster.context)
            .load(movie.image)
            .into(poster)

        movieName.text = movie.title
        actors.text = movie.description
        itemView.setOnClickListener { clickListener.onMovieClick(movie) }
    }
}