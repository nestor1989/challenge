package com.example.challengeapp.main.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.challengeapp.main.utils.Converters
import com.google.gson.annotations.SerializedName

@Entity (tableName = "news")
@TypeConverters(Converters::class)
data class News(
    @ColumnInfo(name = "id")
    var id: Long,
    @PrimaryKey(autoGenerate = true)
    var entityKey: Long? = null,
    @ColumnInfo(name = "title")
    var title: String,
    @SerializedName("abstract")
    @ColumnInfo(name = "abstractField")
    var abstractField: String,
    @ColumnInfo(name = "media")
    var media: List<Media>,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "favorite")
    var favorite:Boolean = false
){
    constructor() : this(0, null, "", "", emptyList(), "", false)
}

data class Media(
    @SerializedName("media-metadata")
    var mediaMetadata: List<MediaMetadata>
)

data class MediaMetadata(
    var url: String
)
