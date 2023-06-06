package com.example.cooklette.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredients (
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "id_recipe",
        entityColumn = "id_recipe"
    )
    val ingredients: List<Ingredient>
)