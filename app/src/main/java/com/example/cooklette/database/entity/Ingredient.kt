package com.example.cooklette.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient (
    @PrimaryKey(autoGenerate = true)
    val id_ingredient: Int = 0,
    val ingredient: String
)