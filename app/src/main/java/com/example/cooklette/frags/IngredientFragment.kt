package com.example.cooklette.frags

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.cooklette.databinding.FragmentAddBinding
import com.example.cooklette.databinding.IngredientRowBinding

class IngredientFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val ingredientList = mutableListOf<Pair<String, Int>>()
    private val rowBindings = mutableListOf<IngredientRowBinding>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddIngredient.setOnClickListener {
            addIngredientRow()
        }

        binding.buttonSave.setOnClickListener {
            val title = binding.getRecipyName.text.toString()
            val instruction = binding.getInstructions.text.toString()

            rowBindings.forEach{
                val ingredientName = it.getIngredientName.text.toString()
                val ingredientQuantity = it.getIngredientQuantity.text.toString().toInt()
                ingredientList.add(ingredientName to ingredientQuantity)

            }

            if (title == "") {
                Toast.makeText(context, "Missing Title", Toast.LENGTH_SHORT).show()
            } else if (instruction == "") {
                Toast.makeText(context, "Missing Recipe Instructions", Toast.LENGTH_SHORT).show()
            } else if (ingredientList.isEmpty()) {
                Toast.makeText(context, "No Ingredients Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Recipe Added", Toast.LENGTH_SHORT).show()
                // Use title, instruction, and ingredientList as needed
                d("ingredients", "ingredients $ingredientList")
            }
        }
    }

    private fun addIngredientRow() {
        val rowBinding =
            IngredientRowBinding.inflate(layoutInflater, binding.containerIngredients, true)

        rowBindings.add(rowBinding)

        rowBinding.buttonRemoveIngredient.setOnClickListener {
            binding.containerIngredients.removeView(rowBinding.root)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
