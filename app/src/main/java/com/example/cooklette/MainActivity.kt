package com.example.cooklette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.lifecycle.lifecycleScope
import com.example.cooklette.database.RecipeDB
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Recipe
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dao: RecipeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = RecipeDB.getInstance(this).recipeDao


        lifecycleScope.launch{
            val all_recipe = dao.getAllRecipe()
            d("mytest", "this is the recipe list\n$all_recipe")
        }
    }

    fun getDao(): RecipeDao{
        return dao
    }
}