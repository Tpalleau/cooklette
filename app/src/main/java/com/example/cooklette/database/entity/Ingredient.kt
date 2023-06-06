package com.example.cooklette.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity

@Entity(primaryKeys = ["id_recipe", "ingredient"])
data class Ingredient(
    @NonNull
    val id_recipe: Long,
    val ingredient: String,
    val unit: String,
    val quantity: Long
)
