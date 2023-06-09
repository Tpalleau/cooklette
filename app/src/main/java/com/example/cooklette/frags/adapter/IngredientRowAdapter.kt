package com.example.cooklette

// RecipeAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.database.dao.RecipeDao
import com.example.cooklette.database.entity.Ingredient
import com.example.cooklette.database.entity.RecipeWithIngredients
import com.example.cooklette.frags.SavedFragmentDirections
import kotlinx.coroutines.launch

class RecipeAdapter(private var items: List<RecipeWithIngredients>, private var dao: RecipeDao, private val lifecycleScope: LifecycleCoroutineScope) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

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
            adapter = IngredientsAdapterView(recipe.ingredients)
            visibility = if (isExpandedList[position]) View.VISIBLE else View.GONE
        }

        holder.ivExpandCollapse.setImageResource(if (isExpandedList[position]) R.drawable.ic_expand_less else R.drawable.ic_expand_more)

        holder.layoutRecipeName.setOnClickListener {
            isExpandedList[position] = !isExpandedList[position]
            holder.rvIngredients.visibility = if (isExpandedList[position]) View.VISIBLE else View.GONE
            holder.ivExpandCollapse.setImageResource(if (isExpandedList[position]) R.drawable.ic_expand_less else R.drawable.ic_expand_more)
        }

        // go to Recipe
        holder.btnNavigate.setOnClickListener {
            val action = SavedFragmentDirections.actionSavedFragmentToRecipeFragment(recipe.recipe.id_recipe.toInt())
            Navigation.findNavController(it).navigate(action)
        }

        // DELETE recipe
        holder.btnImageDelete.setOnClickListener {
            lifecycleScope.launch {
                dao.deleteRecipeWithIngredientsById(recipe.recipe.id_recipe.toInt())
                dao.deleteRecipeById(recipe.recipe.id_recipe.toInt())

                val mutableItems = items.toMutableList()
                mutableItems.removeAt(position)
                items = mutableItems.toList()
                notifyItemRemoved(position)
            }
        }

        holder.btnImageEdit.setOnClickListener{
            val action = SavedFragmentDirections.actionSavedFragmentToAddFragment()
            action.recipeId = recipe.recipe.id_recipe.toInt()
            Navigation.findNavController(it).navigate(action)
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
        val btnImageDelete: ImageButton = itemView.findViewById(R.id.buttonImageDelete)
        val btnImageEdit: ImageButton = itemView.findViewById(R.id.buttonImageEdit)
    }
}

// RecyclerViewAdapter for the ingredients list
class IngredientsAdapterView(private val ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapterView.IngredientViewHolder>() {

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
