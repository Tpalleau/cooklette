package com.example.cooklette.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.Recipe_Ingredient
import com.example.cooklette.database.entity.Unit
import com.example.cooklette.database.entity.relations.IngredientWithRecipe
import com.example.cooklette.database.entity.relations.RecipeWithIngredient

@Dao
interface RecipeDao {
    // Recipe
    @Query("SELECT * FROM Recipe WHERE id=:id ")
    suspend fun getAllRecipeInfo(id: Int): Recipe

    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipe(): List<Recipe>

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    // Ingredients
    @Query("SELECT * FROM Ingredient WHERE id=:id")
    suspend fun getIngredient(id: Int): Ingredient

    // UNIT
    @Query("SELECT * FROM Unit WHERE id=:id")
    suspend fun getUnit(id: Int): Unit

    //Recipe_Ingredient
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(crossRef: Recipe_Ingredient)

    /*
    @Transaction
    @Query("SELECT * FROM Recipe WHERE id=:id_recipe")
    suspend fun getIngredientOfRecipe(id_recipe: Int): List<RecipeWithIngredient>

     */

    /*@Transaction
    @Query("SELECT * FROM Ingredient WHERE id=:id_ingredient")
    suspend fun getRecipeOfIngredient(id_ingredient: Int): List<IngredientWithRecipe>

     */
}
