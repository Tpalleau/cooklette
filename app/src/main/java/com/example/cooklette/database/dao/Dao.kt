package com.example.cooklette.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeWithIngredients
import com.example.cooklette.database.entity.Unit

@Dao
interface RecipeDao {
    // Recipe
    @Query("SELECT * FROM Recipe WHERE id_recipe=:id ")
    suspend fun getRecipe(id: Int): Recipe

    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipies(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    @Query("DELETE FROM Recipe WHERE id_recipe = :id")
    suspend fun deleteRecipeById(id: Int)

    // UNIT
    @Query("SELECT * FROM unit")
    suspend fun getAllUnits(): List<Unit>

    @Query("SELECT * FROM Unit WHERE unit=:unit")
    suspend fun getUnit(unit: String): Unit

    @Insert
    suspend fun insertUnit(unit: Unit)

    //Ingredient
    @Query("SELECT * FROM Ingredient")
    suspend fun getAllIds(): Ingredient

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(crossRef: Ingredient): Long

    @Query("DELETE FROM Ingredient WHERE id_recipe= :id_recipe")
    suspend fun deleteRecipeWithIngredientsById(id_recipe: Int)

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id_recipe = :id_recipe")
    suspend fun getRecipeWithIngredients(id_recipe: Int): RecipeWithIngredients

    @Transaction
    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipeWithIngredients(): List<RecipeWithIngredients>

}
