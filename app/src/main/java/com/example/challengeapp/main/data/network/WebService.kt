package com.example.challengeapp.main.data.network

import com.example.challengeapp.main.data.model.NewsApiResponse
import com.example.challengeapp.main.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("{searchBy}/{period}${Constants.PATH_JSON}")
    suspend fun mostPopular(
        @Path("searchBy")
        searchBy: String,
        @Path("period")
        period: String,
        @Query("api-key")
        apiKey: String = Constants.API_KEY
    ): NewsApiResponse
}