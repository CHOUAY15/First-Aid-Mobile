package com.example.firstaidfront.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstaidfront.R
import com.example.firstaidfront.TrainingActivity
import com.example.firstaidfront.adapter.CategoriesAdapter
import com.example.firstaidfront.adapter.ProgressAdapter
import com.example.firstaidfront.databinding.FragmentHomeBinding
import com.example.firstaidfront.models.Category
import com.example.firstaidfront.models.Training

class HomeFragment : Fragment() {
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var progressAdapter: ProgressAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        setupProgressRecyclerView()
        loadData()
    }

    private fun setupCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter { category ->
            // Navigate to TrainingActivity
            val intent = Intent(requireContext(), TrainingActivity::class.java).apply {
                putExtra("training_name", category.name)
                putExtra("training_icon", category.iconResId)
                putExtra("video_url", category.videoUrl)
            }
            startActivity(intent)
        }

        view?.findViewById<RecyclerView>(R.id.categoriesRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun setupProgressRecyclerView() {
        progressAdapter = ProgressAdapter()
        view?.findViewById<RecyclerView>(R.id.progressRecyclerView)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = progressAdapter
        }
    }

    private fun loadData() {
        // Sample data - replace icon resources with your actual drawable resources
        val categories = listOf(
            Category("First Aid", R.drawable.ic_healthtest, "MKZclIAJV_A"),
            Category("CPR", R.drawable.ic_healthtest, "MKZclIAJV_A"),
            Category("Emergency", R.drawable.ic_healthtest, "MKZclIAJV_A")
        )

        val trainings = listOf(
            Training("Basic First Aid", R.drawable.ic_healthtest, 75),
            Training("CPR Basics", R.drawable.ic_healthtest, 50),
            Training("Emergency Response", R.drawable.ic_healthtest, 25)
        )

        categoriesAdapter.setCategories(categories)
        progressAdapter.setTrainings(trainings)
    }
}