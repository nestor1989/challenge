package com.example.challengeapp.main.data.network

import com.example.challengeapp.main.data.model.NewsApiResponse
import com.example.challengeapp.main.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET(Constants.EMAILED + Constants.API_KEY)
    suspend fun mostPopular(
    ): NewsApiResponse
}