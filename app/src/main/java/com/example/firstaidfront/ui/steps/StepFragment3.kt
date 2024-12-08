package com.example.firstaidfront.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstaidfront.R
import com.example.firstaidfront.databinding.FragmentStep3Binding

class StepFragment3 : Fragment() {
    private var _binding: FragmentStep3Binding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): StepFragment3 {
            return StepFragment3()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Add any specific view setup for Step 3
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}