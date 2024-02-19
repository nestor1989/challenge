package com.example.challengeapp.main.data.model

import com.google.gson.annotations.SerializedName

data class News(
    var id: Long,
    var title: String,
    var abstract: String,
    var media: List<Media>,
    var url: String,
    var favorite:Boolean = false
)

data class Media(
    @SerializedName("media-metadata")
    var mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata(
    var url: String
)
