package com.example.challengeapp.main.domain.favorites

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News

interface GetFavoritesUseCase {
    suspend fun execute(): Resource<List<News>>
}