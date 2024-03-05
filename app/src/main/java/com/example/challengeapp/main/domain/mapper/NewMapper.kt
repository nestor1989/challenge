package com.example.challengeapp.main.domain.mapper

import com.example.challengeapp.main.data.model.Media
import com.example.challengeapp.main.data.model.MediaMetadata
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO

class NewMapper: Mapper<News, NewsDTO> {
    override fun mapToUI(input: News): NewsDTO {
       return NewsDTO(
           id = input.id,
           title = input.title,
           abstractField = input.abstractField,
           mediaUrls = input.media.flatMap { it.mediaMetadata }.map { it.url },
           url = input.url,
           favorite = input.favorite
       )
    }

    override fun mapToDomain(input: NewsDTO): News {
        return News(
            id = input.id,
            title = input.title,
            abstractField = input.abstractField,
            media = input.mediaUrls.map { Media(listOf(MediaMetadata(it))) },
            url = input.url,
            favorite = input.favorite
        )
    }
}