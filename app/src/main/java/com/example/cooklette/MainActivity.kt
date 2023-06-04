package com.example.cooklette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cooklette.database.RecipeDB

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dao = RecipeDB.getInstance(this).recipeDao()


    }
}