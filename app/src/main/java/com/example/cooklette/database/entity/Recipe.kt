package com.example.cooklette.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe (
    @PrimaryKey(autoGenerate = true)
    val id_recipe: Long = 0,
    val name: String,
    val instructions: String
)