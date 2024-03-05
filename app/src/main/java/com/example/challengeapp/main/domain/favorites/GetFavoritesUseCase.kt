package com.example.challengeapp.main.domain.favorites
import com.example.challengeapp.main.data.model.News

interface GetFavoritesUseCase {
    suspend operator fun invoke(): List<News>
}