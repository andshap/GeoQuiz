package com.example.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.geoquiz.databinding.ActivityCheatBinding

const val EXTRA_ANSWER_SHOWN = "com.example.android.geoiquiz.answer_shown"

const val REMAIN_CHEATS_MINUS = "com.example.android.geoiquiz.remain_cheats_minus"

private const val EXTRA_ANSWER_IS_TRUE = "com.example.android.geoquiz.answer_is_true"

private const val CHEATED = "com.example.android.geoquiz.cheated"

class CheatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheatBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        quizViewModel.answerShown = intent.getBooleanExtra(CHEATED, false)

        quizViewModel.remainCheats = intent.getIntExtra(REMAIN_CHEATS_MINUS, 3)

        val answerText = when {
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }

        if (quizViewModel.answerShown) {
            binding.answerTextView.setText(answerText)
        }

        binding.showAnswerButton.setOnClickListener {
            if (!quizViewModel.answerShown) {
                binding.answerTextView.setText(answerText)
                quizViewModel.answerShown = true
                quizViewModel.remainCheats = quizViewModel.remainCheats - 1
                setAnswerSownResult(quizViewModel.answerShown, quizViewModel.remainCheats)
            }
        }

    }

    private fun setAnswerSownResult(isAnswerShown: Boolean, remainCheats: Int) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            putExtra(REMAIN_CHEATS_MINUS, remainCheats)
        }
        setResult(Activity.RESULT_OK, data)

    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean,
                      cheated: Boolean, remainCheats: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(CHEATED, cheated)
                putExtra(REMAIN_CHEATS_MINUS, remainCheats)
            }
        }
    }
}