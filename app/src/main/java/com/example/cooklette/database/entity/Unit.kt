package com.example.cooklette.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Unit (
    @PrimaryKey(autoGenerate = false)
    val unit: String
        )