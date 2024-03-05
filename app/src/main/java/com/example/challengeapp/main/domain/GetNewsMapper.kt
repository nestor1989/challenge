package com.example.challengeapp.main.domain

import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO

class GetNewsMapper : Mapper<List<News>, List<NewsDTO>> {
    override fun map(input: List<News>): List<NewsDTO> {
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
}