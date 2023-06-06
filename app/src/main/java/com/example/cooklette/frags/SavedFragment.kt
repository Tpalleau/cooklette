package com.example.cooklette.frags

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.MainActivity
import com.example.cooklette.R
import com.example.cooklette.RecipeAdapter
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.RecipeIngredient
import com.example.cooklette.database.entity.RecipeWithIngredients
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {
    private lateinit var dao: RecipeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_saved, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        var data: List<RecipeWithIngredients>
        lifecycleScope.launch{
            data = dao.getAllRecipeWithIngredients()
            val adapter = RecipeAdapter(data)
            recyclerView.adapter = adapter
        }


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = (context as MainActivity).getDao()
    }

}