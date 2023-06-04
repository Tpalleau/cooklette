package com.example.cooklette.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient
import com.example.cooklette.database.entity.Unit
import com.example.cooklette.database.entity.relations.IngredientWithRecipe
import com.example.cooklette.database.entity.relations.RecipeWithIngredient

@Dao
interface RecipeDao {
    // Recipe
    @Query("SELECT * FROM Recipe WHERE id_recipe=:id ")
    suspend fun getAllRecipeInfo(id: Int): Recipe

    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipe(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe): Long

    // Ingredients
    @Query("SELECT * FROM ingredient")
    suspend fun getAllIngredients(): List<Ingredient>

    @Query("SELECT * FROM Ingredient WHERE id_ingredient=:id")
    suspend fun getIngredient(id: Int): Ingredient

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: Ingredient) : Long

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
    @Query("SELECT * FROM Recipe WHERE id_recipe=:id_recipe")
    suspend fun getIngredientOfRecipe(id_recipe: Int): List<RecipeWithIngredient>

    @Transaction
    @Query("SELECT * FROM Ingredient WHERE id_ingredient=:id_ingredient")
    suspend fun getRecipeOfIngredient(id_ingredient: Int): List<IngredientWithRecipe>


}
