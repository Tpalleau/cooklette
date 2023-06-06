package com.example.cooklette

// RecipeAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.database.entity.RecipeIngredient
import com.example.cooklette.database.entity.RecipeWithIngredients

class RecipeAdapter(private val items: List<RecipeWithIngredients>) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private val isExpandedList: MutableList<Boolean> = MutableList(items.size) { false }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = items[position]
        holder.tvRecipeTitle.text = recipe.recipe.name

        holder.rvIngredients.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = IngredientsAdapter(recipe.ingredients)
            visibility = if (isExpandedList[position]) View.VISIBLE else View.GONE
        }

        holder.ivExpandCollapse.setImageResource(if (isExpandedList[position]) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

        holder.layoutRecipeName.setOnClickListener {
            isExpandedList[position] = !isExpandedList[position]
            holder.rvIngredients.visibility = if (isExpandedList[position]) View.VISIBLE else View.GONE
            holder.ivExpandCollapse.setImageResource(if (isExpandedList[position]) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
        }

        holder.btnNavigate.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // ViewHolder for the recipe_item.xml layout
    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecipeTitle: TextView = itemView.findViewById(R.id.recipeName)
        val ivExpandCollapse: ImageView = itemView.findViewById(R.id.ivExpandCollapse)
        val layoutRecipeName: LinearLayout = itemView.findViewById(R.id.layoutRecipeName)
        val rvIngredients: RecyclerView = itemView.findViewById(R.id.rvIngredients)
        val btnNavigate: Button = itemView.findViewById(R.id.btnSeeRecipe)
    }

    // RecyclerViewAdapter for the ingredients list
    class IngredientsAdapter(private val ingredients: List<RecipeIngredient>) :
        RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val itemView = inflater.inflate(R.layout.ingredient_item, parent, false)
            return IngredientViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
            val ingredient = ingredients[position]
            holder.tvIngredientName.text = ingredient.ingredient
        }

        override fun getItemCount(): Int {
            return ingredients.size
        }

        class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvIngredientName: TextView = itemView.findViewById(R.id.ingredientName)
        }
    }
}
