package com.example.firstaidfront.ui.steps

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstaidfront.R
import com.example.firstaidfront.adapter.CourseItemAdapter
import com.example.firstaidfront.databinding.FragmentStep2Binding
import com.example.firstaidfront.models.CourseItem

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StepFragment2 : Fragment() {
    private var _binding: FragmentStep2Binding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): StepFragment2 {
            return StepFragment2()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupDownloadButton()
    }

    private fun setupRecyclerView() {
        val courseItems = listOf(
            CourseItem(
                "Understanding First Aid Basics",
                "First aid is the initial care given to an injured or sick person before professional medical treatment becomes available.",
                R.drawable.ic_healthtest
            ),
            CourseItem(
                "Emergency Assessment",
                "Quickly evaluate the scene and the person's condition to ensure safety for both the helper and the injured.",
                R.drawable.ic_healthtest
            ),
            CourseItem(
                "Basic Life Support",
                "Learn critical techniques to maintain vital functions and prevent further injury until professional help arrives.",
                R.drawable.ic_healthtest
            ),
            CourseItem(
                "Handling Different Scenarios",
                "Different emergencies require different approaches. Learn how to respond to various medical situations effectively.",
                null
            )
        )

        val adapter = CourseItemAdapter(courseItems)
        binding.recyclerViewCourseContent.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCourseContent.adapter = adapter
    }

    private fun setupDownloadButton() {
        binding.btnDownloadResource.setOnClickListener {
            createAndSharePDF()
        }
    }

    private fun createAndSharePDF() {
        try {
            // This is a simplified example. In a real app, you'd use a PDF generation library
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val currentDateTime = dateFormat.format(Date())

            val pdfContent = """
                First Aid Training Resource
                
                Generated on: $currentDateTime
                
                Course Overview:
                - Comprehensive first aid training
                - Critical emergency response techniques
                - Practical skills for life-saving interventions
            """.trimIndent()

            // Save to a temporary file
            val file = File(requireContext().cacheDir, "FirstAidTrainingResource.txt")
            file.writeText(pdfContent)

            // Share the file
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(shareIntent, "Share First Aid Resource"))

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "No app found to handle the file", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error creating resource", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}