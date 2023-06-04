package com.example.cooklette.database.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id_recipe", "id_ingredient"])
data class RecipeIngredient(
    @NonNull
    val id_recipe: Long,
    @NonNull
    @ColumnInfo(index = true)
    val id_ingredient: Long,
    @NonNull
    val id_unit: Long,
    @NonNull
    val quantity: Long
)
