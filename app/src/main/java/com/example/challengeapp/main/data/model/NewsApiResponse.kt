package com.example.challengeapp.main.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class NewsApiResponse (
    var status:String?=null,
    @SerializedName("num_results")
    var numResults:Int?=null,
    var results:List<News>
)