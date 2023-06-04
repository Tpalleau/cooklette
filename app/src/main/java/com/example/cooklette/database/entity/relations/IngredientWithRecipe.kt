package com.example.cooklette.database.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient

data class IngredientWithRecipe (
    @Embedded val ingredient: Ingredient,

    @Relation(
        parentColumn = "id_ingredient",
        entityColumn = "id_recipe",
        associateBy = Junction(RecipeIngredient::class)
    ) val recipe: List<Recipe>

)