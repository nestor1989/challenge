package com.example.challengeapp.main.data.network

import com.example.challengeapp.main.data.model.NewsApiResponse
import com.example.challengeapp.main.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("{searchBy}" + "{period} " + Constants.PATH_JSON + Constants.API_KEY)
    suspend fun mostPopular(
        @Path("searchBy")
        searchBy: String,
        @Path("period")
        period: String
    ): NewsApiResponse
}