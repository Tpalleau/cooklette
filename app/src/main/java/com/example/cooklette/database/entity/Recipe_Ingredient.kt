package com.example.cooklette.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["id_recipe", "id_ingredient"])
data class Recipe_Ingredient(
    val id_recipe: Int,
    val id_ingredient: Int,
    val id_unit: Int,
    val quantity: Int
)
