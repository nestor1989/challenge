package com.example.challengeapp.main.domain

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News

interface FetchMostPopularUseCase {
    suspend fun execute(searchBy: String, period: String): Resource<List<News>>
}