package com.example.challengeapp.main.data.model

data class NewsDTO(
    val id: Long,
    val title: String,
    val abstractField: String,
    val mediaUrls: List<String>,
    val url: String,
    val favorite: Boolean
)