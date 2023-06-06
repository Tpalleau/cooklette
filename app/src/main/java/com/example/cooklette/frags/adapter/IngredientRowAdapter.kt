package com.example.cooklette.frags.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cooklette.R
import com.example.cooklette.database.entity.Recipe

class IngredientRowAdapter(private val data: List<Recipe>) : RecyclerView.Adapter<IngredientRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_view_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setTitle.text = data[position].name
        holder.setInstructions.text = data[position].instructions
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setTitle: TextView = itemView.findViewById(R.id.setTitle)
        val setInstructions: TextView = itemView.findViewById(R.id.setInstructions)
    }
}