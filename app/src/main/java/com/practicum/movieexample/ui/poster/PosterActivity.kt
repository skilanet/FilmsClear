package com.practicum.movieexample.ui.poster

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.practicum.movieexample.R

class PosterActivity : AppCompatActivity() {

    private lateinit var poster: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poster)

        poster = findViewById(R.id.poster)
        val url = intent.extras?.getString("poster", "")

        Glide.with(applicationContext).load(url).into(poster)
    }
}