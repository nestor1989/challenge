package com.example.challengeapp.main.domain.mapper

import com.example.challengeapp.main.data.model.Media
import com.example.challengeapp.main.data.model.MediaMetadata
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO

class GetNewsMapper : Mapper<List<News>, List<NewsDTO>> {
    override fun mapToUI(input: List<News>): List<NewsDTO> {
        return input.map { news ->
            NewsDTO(
                id = news.id,
                title = news.title,
                abstractField = news.abstractField,
                mediaUrls = news.media.flatMap { it.mediaMetadata }.map { it.url },
                url = news.url,
                favorite = news.favorite
            )
        }
    }


    override fun mapToDomain(input: List<NewsDTO>): List<News> {
        return input.map { newsDTO ->
            News(
                id = newsDTO.id,
                title = newsDTO.title,
                abstractField = newsDTO.abstractField,
                media = newsDTO.mediaUrls.map { Media(listOf(MediaMetadata(it))) },
                url = newsDTO.url,
                favorite = newsDTO.favorite
            )
        }
    }
}