package com.example.geoquiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel


const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

const val ANSWER_SHOWN = "ANSWER_SHOWN"

const val REMAIN_CHEATS = "REMAIN_CHEATS"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel()  {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true),
    )

    var remainCheats: Int
        get() = savedStateHandle.get(REMAIN_CHEATS) ?: 3
        set(value) = savedStateHandle.set(REMAIN_CHEATS, value)

    var isCheater: Boolean
        get() = savedStateHandle.get(currentIndex.toString()) ?: false
        set(value) = savedStateHandle.set(currentIndex.toString(), value)

    var answerShown: Boolean
        get() = savedStateHandle.get(ANSWER_SHOWN) ?: false
        set(value) = savedStateHandle.set(ANSWER_SHOWN, value)

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}