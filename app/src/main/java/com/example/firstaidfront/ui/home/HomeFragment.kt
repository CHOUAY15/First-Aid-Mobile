package com.example.firstaidfront.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

import androidx.lifecycle.lifecycleScope
import androidx.fragment.app.viewModels
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.firstaidfront.TrainingActivity
import com.example.firstaidfront.adapter.CategoriesAdapter
import com.example.firstaidfront.adapter.ProgressAdapter
import com.example.firstaidfront.databinding.FragmentHomeBinding

import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var progressAdapter: ProgressAdapter

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        setupProgressRecyclerView()
        setupObservers()

        viewModel.loadTrainings()
    }

    private fun setupCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter { category ->
            val intent = Intent(requireContext(), TrainingActivity::class.java).apply {
                putExtra("training_name", category.title)
                putExtra("training_icon", category.iconResId)
                putExtra("video_url", category.urlYtb)
                putExtra("goals", category.goals)
                putExtra("training_id", category.id)
            }
            startActivity(intent)
        }

        binding.categoriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun setupProgressRecyclerView() {
        progressAdapter = ProgressAdapter()
        binding.progressRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = progressAdapter
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.categories.collect { categories ->
                        categoriesAdapter.setCategories(categories)
                    }
                }

                launch {
                    viewModel.trainings.collect { trainings ->
                        progressAdapter.setTrainings(trainings)
                    }
                }

                launch {
                    viewModel.isLoading.collect { isLoading ->
//                        binding.loadingProgress?.isVisible = isLoading
                    }
                }

                launch {
                    viewModel.error.collect { errorMessage ->
                        errorMessage?.let {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}