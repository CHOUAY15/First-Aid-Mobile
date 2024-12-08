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
    }

    private fun getVideoIdForStep(): String {
        // Add logic to return different video IDs based on step number
        return when(stepNumber) {
            1 -> "VIDEO_ID_1"
            2 -> "VIDEO_ID_2"
            3 -> "VIDEO_ID_3"
            else -> "VIDEO_ID_1"
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