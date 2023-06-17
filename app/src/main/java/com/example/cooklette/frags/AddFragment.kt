package com.example.cooklette.frags

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.cooklette.MainActivity
import com.example.cooklette.R
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.Unit
import com.example.cooklette.databinding.FragmentAddBinding
import com.example.cooklette.databinding.IngredientRowBinding
import kotlinx.coroutines.launch

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: RecipeDao
    private val args: AddFragmentArgs by navArgs()

    private val ingredientList = mutableListOf<Triple<String, Long, String>>()
    private val rowBindings = mutableListOf<IngredientRowBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inflate the custom toolbar layout
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        inflater.inflate(R.layout.toolbar_add, toolbar)

        // Set the custom toolbar as the activity's toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        d("myinfo", "SETTING button back pressed")
        toolbar.findViewById<ImageButton>(R.id.imageButtonBack).setOnClickListener{
            Navigation.findNavController(view)
                .navigate(R.id.action_addFragment_to_savedFragment)
            d("myinfo", "button back pressed")
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = (context as MainActivity).getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = args.recipeId
        if (recipeId != -1) {
            lifecycleScope.launch {
                val recipeWithIngredients = dao.getRecipeWithIngredients(recipeId)
                if (recipeWithIngredients != null) {
                    binding.getRecipyName.setText(recipeWithIngredients.recipe.name)
                    binding.getInstructions.setText(recipeWithIngredients.recipe.instructions)
                    binding.getNbPeople.setText(recipeWithIngredients.recipe.nb_people.toString())

                    binding.containerIngredients.removeAllViews()
                    rowBindings.clear()

                    recipeWithIngredients.ingredients.forEach { ingredient ->
                        addIngredientRow().apply {
                            getIngredientName.setText(ingredient.ingredient)
                            getIngredientQuantity.setText(ingredient.quantity.toString())

                            val unitPosition = getIndex(getUnit, ingredient.unit)
                            getUnit.setSelection(unitPosition)
                        }
                    }
                }
            }
        }

        binding.buttonAddIngredient.setOnClickListener {
            addIngredientRow()
        }

        binding.buttonSave.setOnClickListener {
            val title: String = binding.getRecipyName.text.toString()
            val instruction: String = binding.getInstructions.text.toString()
            val nb_people: String = binding.getNbPeople.text.toString()
            d("nb_value", "val $nb_people")

            rowBindings.forEach {
                ingredientList.add(
                    Triple(
                        it.getIngredientName.text.toString(),
                        it.getIngredientQuantity.text.toString().toLong(),
                        it.getUnit.selectedItem as String
                    )
                )
            }

            if (title == "") {
                Toast.makeText(context, "Missing Title", Toast.LENGTH_SHORT).show()
            } else if (nb_people == "") {
                Toast.makeText(context, "Missing number of people", Toast.LENGTH_SHORT).show()
            } else if (ingredientList.isEmpty()) {
                Toast.makeText(context, "No Ingredients Added", Toast.LENGTH_SHORT).show()
            } else if (instruction == "") {
                Toast.makeText(context, "Missing Recipe Instructions", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Recipe Added", Toast.LENGTH_SHORT).show()

                lifecycleScope.launch {
                    val idRecipe: Long
                    if (args.recipeId != -1) {
                        // Update existing recipe
                        idRecipe = args.recipeId.toLong()
                        // clear old ingredients and recipe name
                        dao.deleteRecipeWithIngredientsById(idRecipe.toInt())
                        dao.insertRecipe(Recipe(idRecipe, title, instruction, nb_people.toInt()))
                    } else {
                        // Insert new recipe
                        idRecipe = dao.insertRecipe(Recipe(name = title, instructions = instruction, nb_people = nb_people.toInt()))
                    }

                    d("MyInfo", "ingredientList $ingredientList, ${ingredientList.size}")
                    for (i: Int in 0 until ingredientList.size) {
                        dao.insertRecipeIngredient(
                            Ingredient(
                                id_recipe = idRecipe,
                                ingredient = ingredientList[i].first,
                                quantity = ingredientList[i].second,
                                unit = ingredientList[i].third
                            )
                        )
                    }
                    ingredientList.clear()
                }
            }
        }
    }

    private fun addIngredientRow(): IngredientRowBinding {
        val rowBinding = IngredientRowBinding.inflate(layoutInflater, binding.containerIngredients, true)
        rowBindings.add(rowBinding)

        var units: List<Unit>
        lifecycleScope.launch {
            units = dao.getAllUnits()

            val unitStrings = units.map { it.unit }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, unitStrings)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rowBinding.getUnit.adapter = adapter
        }

        rowBinding.buttonRemoveIngredient.setOnClickListener {
            // retirer element de la list
            rowBindings.remove(rowBinding)
            binding.containerIngredients.removeView(rowBinding.root)

        }

        return rowBinding
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
