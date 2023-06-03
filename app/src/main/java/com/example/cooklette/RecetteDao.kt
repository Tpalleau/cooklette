package com.example.cooklette

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecetteDao {
    @Query("SELECT * FROM Recette where id=(:id) ")
    fun getAllRecipyInfo(id: Int): List<Recette>

    @Insert
    fun insertRecipy(recette: Recette)
}