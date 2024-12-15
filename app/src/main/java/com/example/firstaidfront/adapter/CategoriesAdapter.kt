package com.example.firstaidfront.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstaidfront.R
import com.example.firstaidfront.config.ApiClient
import com.example.firstaidfront.models.Category

class CategoriesAdapter(private val onItemClick: (Category) -> Unit) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {

    private var categories = listOf<Category>()

    fun setCategories(categories: List<Category>) {
        this.categories = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    class CategoryViewHolder(
        itemView: View,
        private val onItemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.categoryIcon)
        private val nameView: TextView = itemView.findViewById(R.id.categoryName)

        fun bind(category: Category) {
            nameView.text = category.title

            // Construct the full image URL
            val imageUrl = "${ApiClient.BASE_URL}TRAINING-SERVICE/api/images/${category.iconPath}"

            // Load image using Glide
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_healthtest) // Show default image while loading
                .error(R.drawable.ic_healthtest) // Show default image if loading fails
                .centerCrop()
                .into(iconView)

            itemView.setOnClickListener { onItemClick(category) }
        }
    }
}