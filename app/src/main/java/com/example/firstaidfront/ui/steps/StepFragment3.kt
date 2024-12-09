package com.example.firstaidfront.ui.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.firstaidfront.R
import com.example.firstaidfront.databinding.FragmentStep3Binding
import com.example.firstaidfront.models.TestQuestion
import com.google.android.material.card.MaterialCardView

class StepFragment3 : Fragment() {
    private var _binding: FragmentStep3Binding? = null
    private val binding get() = _binding!!

    private var currentQuestionIndex = 0
    private val questions = listOf(
        TestQuestion(
            1,
            "What is the first step in assessing an emergency situation?",
            listOf(
                "Start CPR immediately",
                "Check for scene safety",
                "Call emergency services",
                "Check for breathing"
            )
        ),
        TestQuestion(
            2,
            "What is the correct compression to breath ratio in adult CPR?",
            listOf(
                "15:2",
                "30:2",
                "20:2",
                "10:2"
            )
        ),
        TestQuestion(
            3,
            "Which of the following is a sign of cardiac arrest?",
            listOf(
                "Sudden collapse",
                "Severe headache",
                "Nausea",
                "Dizziness"
            )
        ),
        TestQuestion(
            4,
            "How long should you check for breathing in an unconscious person?",
            listOf(
                "5 seconds",
                "10 seconds",
                "15 seconds",
                "20 seconds"
            )
        ),
        TestQuestion(
            5,
            "What is the correct position for recovery position?",
            listOf(
                "On their back",
                "On their side with top leg bent",
                "On their stomach",
                "Sitting upright"
            )
        )
    )

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
        setupAnimation()
        displayQuestion(currentQuestionIndex)
        setupSubmitButton()
    }

    private fun setupAnimation() {
        binding.testAnimation.setAnimation(R.raw.quiz)
        binding.testAnimation.playAnimation()
    }

    private fun displayQuestion(index: Int) {
        val question = questions[index]
        binding.questionNumber.text = "Question ${index + 1}/${questions.size}"
        binding.questionText.text = question.question

        binding.optionsGroup.removeAllViews()

        question.options.forEachIndexed { optionIndex, optionText ->
            val optionView = layoutInflater.inflate(
                R.layout.item_test_option,
                binding.optionsGroup,
                false
            ) as MaterialCardView

            val radioButton = optionView.findViewById<RadioButton>(R.id.radioButton)
            radioButton.text = optionText
            radioButton.isChecked = question.selectedAnswer == optionIndex

            // Change this part to handle both the card and radio button clicks
            val clickListener = View.OnClickListener {
                // Uncheck all radio buttons first
                for (i in 0 until binding.optionsGroup.childCount) {
                    val cardView = binding.optionsGroup.getChildAt(i) as MaterialCardView
                    cardView.findViewById<RadioButton>(R.id.radioButton).isChecked = false
                }

                // Check the selected radio button
                radioButton.isChecked = true

                // Save the answer and move to next question
                handleOptionSelection(optionIndex)
            }

            // Set click listeners for both the card and the radio button
            optionView.setOnClickListener(clickListener)
            radioButton.setOnClickListener(clickListener)

            binding.optionsGroup.addView(optionView)
        }

        binding.submitButton.visibility =
            if (index == questions.size - 1) View.VISIBLE else View.GONE
    }

    private fun handleOptionSelection(selectedIndex: Int) {
        // Save the selected answer
        questions[currentQuestionIndex].selectedAnswer = selectedIndex

        // Add a small delay before moving to the next question
        view?.postDelayed({
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion(currentQuestionIndex)
            }
        }, 300) // 300ms delay for better UX
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            val unansweredQuestions = questions.count { it.selectedAnswer == null }

            if (unansweredQuestions > 0) {
                Toast.makeText(
                    context,
                    "Please answer all questions before submitting",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Calculate score
            val score = questions.count {
                it.selectedAnswer == 1 // Assuming index 1 is correct for all questions
            }

            showResultDialog(score)
        }
    }

    private fun showResultDialog(score: Int) {
        val percentage = (score.toFloat() / questions.size) * 100

        val dialogView = layoutInflater.inflate(R.layout.dialog_test_result, null)
        // Setup dialog view...

        // Show dialog with score and next steps
        // You can create a custom dialog layout and implementation
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): StepFragment3 = StepFragment3()
    }
}