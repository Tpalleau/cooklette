package com.example.cooklette.database.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient

data class RecipeWithIngredient (
    @Embedded val recipe: Recipe,

    @Relation(
        parentColumn = "id_recipe",
        entityColumn = "id_ingredient",
        associateBy = Junction(RecipeIngredient::class)
    ) val ingredient: List<Ingredient>

)