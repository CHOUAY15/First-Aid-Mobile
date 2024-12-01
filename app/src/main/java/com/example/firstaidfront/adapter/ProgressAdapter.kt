package com.example.firstaidfront.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firstaidfront.R
import com.example.firstaidfront.models.Training

class ProgressAdapter : RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {
    private var trainings = listOf<Training>()

    fun setTrainings(trainings: List<Training>) {
        this.trainings = trainings
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_progress, parent, false)
        return ProgressViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(trainings[position])
    }

    override fun getItemCount() = trainings.size

    class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iconView: ImageView = itemView.findViewById(R.id.trainingIcon)
        private val nameView: TextView = itemView.findViewById(R.id.trainingName)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.trainingProgress)

        fun bind(training: Training) {
            iconView.setImageResource(training.iconResId)
            nameView.text = training.name
            progressBar.progress = training.progress
        }
    }
}