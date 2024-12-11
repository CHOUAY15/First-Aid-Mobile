package com.example.firstaidfront

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.firstaidfront.databinding.ActivityTestDetailBinding
import com.google.android.material.elevation.SurfaceColors

class TestDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupWindowDecoration()
        setupTestDetails()
    }

    private fun setupToolbar() {
        binding.toolbar.apply {
            setNavigationOnClickListener { finish() }
            setNavigationIconTint(ContextCompat.getColor(context, R.color.white))
        }
    }

    private fun setupWindowDecoration() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.pink_dark)
        window.navigationBarColor = SurfaceColors.SURFACE_2.getColor(this)
    }

    private fun setupTestDetails() {
        // Get data from intent
        val testId = intent.getIntExtra("test_id", -1)
        val formationName = intent.getStringExtra("formation_name")

        // Set formation name
        binding.formationName.text = formationName

        // TODO: Load test details using testId
    }
}