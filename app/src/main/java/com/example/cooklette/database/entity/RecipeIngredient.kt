package com.example.cooklette.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["id_recipe", "id_ingredient"])
data class RecipeIngredient(
    @NonNull
    val id_recipe: Int,
    @NonNull
    @ColumnInfo(index = true)
    val id_ingredient: Int,
    @NonNull
    val id_unit: Int,
    @NonNull
    val quantity: Int
)
