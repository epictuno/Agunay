package com.project.agunay.adapter.kotlin.screens.triviascreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.kotlin.configuration.CurrentQuizz
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.Question
import com.project.agunay.domain.Quizz
import com.project.agunay.domain.User

class TriviaScreenVM: ViewModel() {
    private val _currentQuizz = MutableLiveData<Quizz?>()
    val currentQuizz: LiveData<Quizz?> = _currentQuizz

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question?> = _currentQuestion

    private val _questionAnswered = MutableLiveData<Boolean>(true)
    val questionAnswered: LiveData<Boolean?> = _questionAnswered

    private val _showQuestionAnswers = MutableLiveData<Boolean>(false)
    val showQuestionAnswers: LiveData<Boolean?> = _showQuestionAnswers

    private val _shuffleAnswers = MutableLiveData<Boolean>(true)
    val shuffleAnswers: LiveData<Boolean?> = _shuffleAnswers

    private val _currentMarkedAnswers = MutableLiveData<Int>(0)
    val currentMarkedAnswers: LiveData<Int> = _currentMarkedAnswers

    private val _markedAnswers = MutableLiveData<ArrayList<Boolean>>(arrayListOf(false, false, false, false))
    val markedAnswers: LiveData<ArrayList<Boolean>> = _markedAnswers

    fun setCurrentQuiz(quizz: CurrentQuizz) {
        _currentQuizz.value = quizz.getQuizz()
    }

    fun setCurrentUser(user: CurrentUser) {
        _currentUser.postValue(user.getUser())
    }

    fun getQuestion() {
        if (questionAnswered.value == true) {
            _currentQuestion.value = currentQuizz.value?.questions?.random()
            _questionAnswered.value = false
        }
    }

    fun shuffleAnswers() {
        if (_shuffleAnswers.value == true) {
            _currentQuestion.value?.answers?.shuffle()
            _shuffleAnswers.value = false
        }
    }

    fun checkAnswer(isAnswer: Boolean, answerNumber: Int) {
        Log.d("QuizDebug", (currentQuestion.value?.questionTitle ?: "") + ": " + isAnswer.toString())
        if (questionAnswered.value == false) {

            if (currentQuestion.value?.questionType == "SingleChoice") {
                _questionAnswered.value = true
                _showQuestionAnswers.value = true

                if (isAnswer) {
                    _currentQuizz.value?.correctAnswers = _currentQuizz.value?.correctAnswers?.inc()!!
                    _currentQuizz.value?.correctQuestions?.add(currentQuestion.value)
                }
                else {
                    _currentQuizz.value?.wrongQuestions?.add(currentQuestion.value)
                }
                currentQuizz.value?.removeQuestion(currentQuestion.value)
            }
            else if(currentQuestion.value?.questionType == "MultipleChoice") {
                if (!isAnswer) {
                    _questionAnswered.value = true
                    _showQuestionAnswers.value = true
                    _currentQuizz.value?.wrongQuestions?.add(currentQuestion.value)
                    currentQuizz.value?.removeQuestion(currentQuestion.value)
                }
                else {
                    if (_markedAnswers.value?.get(answerNumber) == false) {
                        _markedAnswers.value?.set(answerNumber, true)
                        _currentMarkedAnswers.value = _currentMarkedAnswers.value?.inc()
                    }
                    if (_currentMarkedAnswers.value == _currentQuestion.value?.numberOfAnswers) {
                        _questionAnswered.value = true
                        _showQuestionAnswers.value = true
                        _currentQuizz.value?.correctAnswers = _currentQuizz.value?.correctAnswers?.inc()!!
                        _currentQuizz.value?.correctQuestions?.add(currentQuestion.value)
                        currentQuizz.value?.removeQuestion(currentQuestion.value)
                    }
                }
            }
        }
        else {
            _showQuestionAnswers.value = false
            _shuffleAnswers.value = true
            _markedAnswers.value = arrayListOf(false, false, false, false)
            getQuestion()
        }
    }
}