package com.example.challengeapp.main.data.model

import com.google.gson.annotations.SerializedName

data class News(
    var id: Long,
    var title: String,
    @SerializedName("asset_id")
    var assetId: Long,
    var url: String,
)
