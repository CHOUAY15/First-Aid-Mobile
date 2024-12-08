package com.example.firstaidfront.ui.test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstaidfront.R
import com.example.firstaidfront.TestDetailActivity
import com.example.firstaidfront.adapter.TestAdapter
import com.example.firstaidfront.databinding.FragmentTestBinding
import com.example.firstaidfront.models.Test


class TestFragment : Fragment() {
    private lateinit var binding: FragmentTestBinding
    private lateinit var testAdapter: TestAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadTestData()
    }

    private fun setupRecyclerView() {
        testAdapter = TestAdapter { test ->
            // Navigate to test details
            val intent = Intent(requireContext(), TestDetailActivity::class.java).apply {
                putExtra("test_id", test.id)
                putExtra("formation_name", test.formationName)
            }
            startActivity(intent)
        }

        binding.testsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = testAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun loadTestData() {
        // Sample data - Replace with your actual data source
        val tests = listOf(
            Test(1, "First Aid Basics", "2024-03-01", true, 18, 20),
            Test(2, "CPR Training", "2024-03-05", false, 14, 20),
            Test(3, "Emergency Response", "2024-03-10", true, 19, 20)
        )
        testAdapter.setTests(tests)
    }
}