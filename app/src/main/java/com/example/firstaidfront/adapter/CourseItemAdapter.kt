package com.example.firstaidfront.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstaidfront.R
import com.example.firstaidfront.databinding.ItemCourseContentBinding
import com.example.firstaidfront.models.CourseItem


class CourseItemAdapter(private val items: List<CourseItem>) :
    RecyclerView.Adapter<CourseItemAdapter.CourseItemViewHolder>() {

    inner class CourseItemViewHolder(private val binding: ItemCourseContentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CourseItem) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            if (item.imageResourceId != null) {
                binding.ivItemImage.visibility = View.VISIBLE
                binding.ivItemImage.setImageResource(item.imageResourceId)
            } else {
                binding.ivItemImage.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseItemViewHolder {
        val binding = ItemCourseContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CourseItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size
}