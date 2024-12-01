package com.example.firstaidfront

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.firstaidfront.databinding.ActivityTrainingBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrainingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTrainingBinding
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from intent
        val trainingName = intent.getStringExtra("training_name") ?: ""
        val trainingIcon = intent.getIntExtra("training_icon", R.drawable.ic_healthtest)
        val videoUrl = intent.getStringExtra("video_url") ?: ""

        setupViews(trainingName, trainingIcon, videoUrl)
        setupVideoPlayer(videoUrl)
    }

    private fun setupViews(trainingName: String, trainingIcon: Int, videoUrl: String) {
        binding.toolbarTitle.text = trainingName
        binding.trainingIcon.setImageResource(trainingIcon)

        binding.buttonNext.setOnClickListener {
            // Handle next button click
            Toast.makeText(this, "Next clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupVideoPlayer(videoUrl: String) {
        youTubePlayerView = binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = extractYouTubeVideoId(videoUrl)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    private fun extractYouTubeVideoId(videoUrl: String): String {
        return videoUrl.substringAfter("youtu.be/")
    }

    override fun onDestroy() {
        super.onDestroy()
        youTubePlayerView.release()
    }
}
