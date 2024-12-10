package com.project.agunay.adapter.kotlin.configuration

import androidx.lifecycle.MutableLiveData


import androidx.lifecycle.LiveData
import com.project.agunay.domain.Quizz

class CurrentQuizz {
    private val _currentQuizz = MutableLiveData<Quizz?>()
    val currentQuizz: LiveData<Quizz?> = _currentQuizz

    fun setQuizz(quizz: Quizz) {
        _currentQuizz.value = quizz
    }

    fun getQuizz(): Quizz? {
        return currentQuizz.value
    }
}