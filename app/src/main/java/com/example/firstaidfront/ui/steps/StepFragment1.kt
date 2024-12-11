package com.example.firstaidfront.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firstaidfront.databinding.FragmentStep1Binding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class StepFragment1 : Fragment() {
    private var _binding: FragmentStep1Binding? = null
    private val binding get() = _binding!!
    private var stepNumber: Int = 1
    private var trainingName: String? = null
    private var videoUrl: String? = null
    private var goals: String? = null


    companion object {
        private const val ARG_STEP_NUMBER = "step_number"

        fun newInstance(stepNumber: Int): StepFragment1 {
            val fragment = StepFragment1()
            val args = Bundle().apply {
                putInt(ARG_STEP_NUMBER, stepNumber)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        stepNumber = arguments?.getInt(ARG_STEP_NUMBER, 1) ?: 1
        trainingName = activity?.intent?.getStringExtra("training_name")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupYouTubePlayer(getVideoIdForStep())
        lifecycle.addObserver(binding.youtubePlayerView)

        // Show AR card only for CPR training
        if (trainingName == "CPR") {
            binding.arCard.visibility = View.VISIBLE
            setupArCard()
        } else {
            binding.arCard.visibility = View.GONE
        }
    }

    private fun setupArCard() {
        binding.arCard.setOnClickListener {
            // Handle AR feature launch here
            // For example, launch AR activity or show AR dialog
        }
    }

    private fun getVideoIdForStep(): String {
        return when (trainingName) {
            "CPR" -> "MKZclIAJV_A"
            "First Aid" -> "xfFf3-8sRAA"
            "Emergency" -> "different_video_id"
            else -> "xfFf3-8sRAA"
        }
    }

    private fun setupYouTubePlayer(videoId: String) {
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}