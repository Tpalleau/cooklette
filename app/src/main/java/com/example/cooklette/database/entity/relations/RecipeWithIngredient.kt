package com.example.cooklette.database.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.Recipe_Ingredient

data class RecipeWithIngredient (
    @Embedded val recipe: List<Recipe>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(Recipe_Ingredient::class)
    ) val ingredient: List<Ingredient>

)