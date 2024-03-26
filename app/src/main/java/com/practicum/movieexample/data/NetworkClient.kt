package com.practicum.movieexample.data

import com.practicum.movieexample.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}