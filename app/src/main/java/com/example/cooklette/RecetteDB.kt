package com.example.cooklette

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recette::class], version = 1)
abstract class RecetteDB: RoomDatabase() {
    abstract fun recetteDao(): RecetteDao
}