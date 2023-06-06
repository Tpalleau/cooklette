package com.example.cooklette.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient
import com.example.cooklette.database.entity.Unit

@Database(entities = [RecipeIngredient::class, Recipe::class, Unit::class], version = 2, exportSchema = false)
abstract class RecipeDB: RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object{
        @Volatile
        private var INSTANCE: RecipeDB? = null

        fun getInstance(context: Context): RecipeDB {
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDB::class.java,
                "recipe_db"
                ).fallbackToDestructiveMigration(
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}