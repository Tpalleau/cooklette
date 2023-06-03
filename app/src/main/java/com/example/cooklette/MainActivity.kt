package com.example.cooklette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = Room.databaseBuilder(
            this, RecetteDB::class.java,"recette_database"
        ).allowMainThreadQueries().build()

        database.recetteDao().insertRecipy(Recette(name = "ragout", instructions = "couper oignon"))
        val recette = database.recetteDao().getAllRecipyInfo(1)

        d("mytest", "dataset size ${recette.size}")
    }
}