package com.example.cooklette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.lifecycle.lifecycleScope
import com.example.cooklette.database.RecipeDB
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Unit
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var dao: RecipeDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = RecipeDB.getInstance(this).recipeDao

        lifecycleScope.launch {
            val units = dao.getAllUnits()
            d("MyInfo", "unit values $units ${units.size}")
            if (units.isEmpty()) {
                d("MyInfo", "unit is null [+] adding values")
                val unitList: List<String> = listOf(
                    "g",
                    "L",
                    "ts",
                    "Ts",
                    "unit"
                )
                unitList.forEach {
                    dao.insertUnit(Unit(unit = it))
                }
            }
        }
    }

    fun getDao(): RecipeDao {
        return dao
    }
}
