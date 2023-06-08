package com.example.cooklette.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.R
import com.example.cooklette.RecipeAdapter
import com.example.cooklette.database.RecipeDB
import com.example.cooklette.database.entity.RecipeWithIngredients
import kotlinx.coroutines.launch

class SavedFragment : Fragment() {
    //private lateinit var dao: RecipeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view =  inflater.inflate(R.layout.fragment_saved, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val dao = RecipeDB.getInstance(view.context).recipeDao
        // get list of all Recipies
        var data: List<RecipeWithIngredients>
        lifecycleScope.launch{
            data = dao.getAllRecipeWithIngredients()
            val adapter = RecipeAdapter(data, dao, lifecycleScope)
            recyclerView.adapter = adapter
        }

        // draw toolbar
        // Inflate the custom toolbar layout
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        inflater.inflate(R.layout.toolbar_saved, toolbar)

        // Set the custom toolbar as the activity's toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.findViewById<ImageButton>(R.id.imageButtonAdd).setOnClickListener{
            Navigation.findNavController(view)
                .navigate(R.id.action_savedFragment_to_addFragment)
        }

        // Customize the toolbar buttons or views based on the fragment

        return view
    }

}