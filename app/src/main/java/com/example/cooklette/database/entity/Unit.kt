package com.example.cooklette.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Unit (
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val unit: String
        )