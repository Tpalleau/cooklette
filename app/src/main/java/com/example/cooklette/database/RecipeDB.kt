package com.example.cooklette.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.Recipe_Ingredient
import com.example.cooklette.database.entity.Unit

@Database(entities = [Recipe::class, Ingredient::class, Unit::class, Recipe_Ingredient::class], version = 2, exportSchema = false)
abstract class RecipeDB: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: RecipeDB? = null

        fun getInstance(context: Context): RecipeDB {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDB::class.java,
                "recipe_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}