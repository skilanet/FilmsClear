package com.practicum.movieexample.ui.movies

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.practicum.movieexample.R
import com.practicum.movieexample.domain.models.Movie

class MovieViewHolder(parent: ViewGroup,private val clickListener: MovieAdapter.MovieClickListener) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.movie_view, parent, false)
) {
    private var poster: ImageView = itemView.findViewById(R.id.poster)
    private var movieName: TextView = itemView.findViewById(R.id.movie_name)
    private var actors: TextView = itemView.findViewById(R.id.actors)
    private var favouriteButton: ImageView = itemView.findViewById(R.id.favouriteButton)

    fun bind(movie: Movie) {
        Glide.with(poster.context)
            .load(movie.image)
            .into(poster)

        movieName.text = movie.title
        actors.text = movie.description
        favouriteButton.setImageDrawable(getFavouriteDrawable(movie.inFavourite))

        itemView.setOnClickListener{ clickListener.onMovieClick(movie)}
        favouriteButton.setOnClickListener{clickListener.onFavouriteToggleClick(movie)}
    }

    private fun getFavouriteDrawable(inFavourite: Boolean): Drawable? {
        return AppCompatResources.getDrawable(itemView.context,
            if (inFavourite) R.drawable.favourite else R.drawable.non_favourite)
    }
}