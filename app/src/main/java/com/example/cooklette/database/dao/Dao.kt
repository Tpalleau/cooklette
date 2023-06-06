package com.example.cooklette.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient
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

    // UNIT
    @Query("SELECT * FROM unit")
    suspend fun getAllUnits(): List<Unit>

    @Query("SELECT * FROM Unit WHERE unit=:unit")
    suspend fun getUnit(unit: String): Unit

    @Insert
    suspend fun insertUnit(unit: Unit)

    //Recipe_Ingredient
    @Query("SELECT * FROM RecipeIngredient")
    suspend fun getAllIds(): RecipeIngredient

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(crossRef: RecipeIngredient): Long

    @Transaction
    @Query("SELECT * FROM Recipe WHERE id_recipe = :id_recipe")
    suspend fun getRecipeWithIngredients(id_recipe: Int): List<RecipeWithIngredients>

    @Transaction
    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipeWithIngredients(): List<RecipeWithIngredients>

}
