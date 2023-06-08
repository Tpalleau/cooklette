package com.example.cooklette.frags

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.R
import com.example.cooklette.database.RecipeDB
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.RecipeWithIngredients
import kotlinx.coroutines.launch

class RecipeFragment : Fragment() {

    private lateinit var dao: RecipeDao
    private lateinit var ingredientsAdapter: IngredientsAdapterView
    private lateinit var numberPicker: NumberPicker

    companion object {
        fun newInstance(recipeId: Int): RecipeFragment {
            val fragment = RecipeFragment()
            val args = Bundle()
            args.putInt("recipeId", recipeId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = RecipeDB.getInstance(requireContext()).recipeDao
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = requireArguments().getInt("recipeId")

        numberPicker = view.findViewById(R.id.numberPicker)
        numberPicker.minValue = 1
        numberPicker.maxValue = 9

        // Fetch the RecipeWithIngredients object from your Room database based on the recipeId
        lifecycleScope.launch {
            val recipeWithIngredients = dao.getRecipeWithIngredients(recipeId)

            // Set the recipe details to corresponding views
            view.findViewById<TextView>(R.id.recipeTitleTextView).text = recipeWithIngredients.recipe.name
            view.findViewById<TextView>(R.id.instructionsTextView).text = recipeWithIngredients.recipe.instructions
            numberPicker.value = recipeWithIngredients.recipe.nb_people

            // Set up RecyclerView for displaying ingredients
            ingredientsAdapter = IngredientsAdapterView(recipeWithIngredients.ingredients, recipeWithIngredients.recipe.nb_people)
            view.findViewById<RecyclerView>(R.id.ingredientsRecyclerView).layoutManager = LinearLayoutManager(requireContext())
            view.findViewById<RecyclerView>(R.id.ingredientsRecyclerView).adapter = ingredientsAdapter

            // Add listener to the NumberPicker
            numberPicker.setOnValueChangedListener { _, _, _ ->
                ingredientsAdapter.nbPeople = numberPicker.value
                ingredientsAdapter.notifyDataSetChanged() // Notify the adapter that data has changed
            }
        }
    }

    // RecyclerViewAdapter for the ingredients list
    class IngredientsAdapterView(private val ingredients: List<Ingredient>, var nbPeople: Int) :
        RecyclerView.Adapter<IngredientsAdapterView.IngredientViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
            return IngredientViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
            val ingredient = ingredients[position]
            holder.bind(ingredient, nbPeople)
        }

        override fun getItemCount(): Int {
            return ingredients.size
        }

        // Nested ViewHolder class
        inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val ingredientTextView: TextView = itemView.findViewById(R.id.ingredientName)

            fun bind(ingredient: Ingredient, nbPeople: Int) {
                val text = "${ingredient.ingredient}  ${ingredient.quantity * nbPeople}${ingredient.unit}"
                ingredientTextView.text = text
            }
        }
    }
}

