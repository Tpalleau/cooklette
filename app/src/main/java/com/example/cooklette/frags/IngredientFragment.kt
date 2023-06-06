package com.example.cooklette.frags

import android.content.Context
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cooklette.MainActivity
import com.example.cooklette.R
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.Recipe
import com.example.cooklette.database.entity.Unit
import com.example.cooklette.databinding.FragmentAddBinding
import com.example.cooklette.databinding.IngredientRowBinding
import kotlinx.coroutines.launch

class IngredientFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var dao: RecipeDao

    private val ingredientList = mutableListOf<Triple<String, Long, String>>()
    private val rowBindings = mutableListOf<IngredientRowBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dao = (context as MainActivity).getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddIngredient.setOnClickListener {
            addIngredientRow()
        }

        binding.buttonSave.setOnClickListener {
            val title: String = binding.getRecipyName.text.toString()
            val instruction: String = binding.getInstructions.text.toString()
            val nb_people: String = binding.getNbPeople.text.toString()
            d("nb_value", "val $nb_people")

            rowBindings.forEach{
                // add values in each row to Triple List
                ingredientList.add(Triple(
                    it.getIngredientName.text.toString(),
                    it.getIngredientQuantity.text.toString().toLong(),
                    it.getUnit.selectedItem as String))
            }

            if (title == "") {
                Toast.makeText(context, "Missing Title", Toast.LENGTH_SHORT).show()
            }else if (nb_people == "") {
                Toast.makeText(context, "Missing number of people", Toast.LENGTH_SHORT).show()
            }else if (ingredientList.isEmpty()) {
            Toast.makeText(context, "No Ingredients Added", Toast.LENGTH_SHORT).show()
            } else if (instruction == "") {
                Toast.makeText(context, "Missing Recipe Instructions", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(context, "Recipe Added", Toast.LENGTH_SHORT).show()



                // add values to db
                lifecycleScope.launch{
                    val idRecipe:Long = dao.insertRecipe(Recipe(name = title, instructions = instruction, nb_people = nb_people.toInt()))

                    d("MyInfo", "ingredientList $ingredientList, ${ingredientList.size}")
                    for (i: Int in 0 until ingredientList.size){
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

    private fun addIngredientRow() {
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
            binding.containerIngredients.removeView(rowBinding.root)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
