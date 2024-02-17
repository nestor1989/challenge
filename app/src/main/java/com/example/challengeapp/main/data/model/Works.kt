package com.example.challengeapp.main.data.model

import androidx.room.PrimaryKey
@androidx.room.Entity
data class Works(
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,
    )
