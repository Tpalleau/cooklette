package com.example.cooklette

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recette (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val instructions: String
)