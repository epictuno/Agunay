package com.project.agunay.adapter.kotlin.screens.mainscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.firebase.ErrorCallback
import com.project.agunay.adapter.firebase.FirebaseQuizzRepository
import com.project.agunay.adapter.firebase.FirebaseShopItemRepository
import com.project.agunay.adapter.firebase.FirebaseUserRepository
import com.project.agunay.adapter.firebase.SuccessCallback
import com.project.agunay.adapter.kotlin.configuration.CurrentQuizz
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.application.repository.QuizzRepository
import com.project.agunay.application.repository.ShopItemRepository
import com.project.agunay.application.repository.UserRepository
import com.project.agunay.domain.Quizz
import com.project.agunay.domain.User

class MainScreenVM(
    private val userRepository: UserRepository = FirebaseUserRepository(),
    private val quizzRepository: QuizzRepository = FirebaseQuizzRepository()
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _currentQuizz = MutableLiveData<Quizz?>()
    val currentQuizz: LiveData<Quizz?> = _currentQuizz

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isTriviaActionTriggered = MutableLiveData<Boolean>()
    val isTriviaActionTriggered: LiveData<Boolean> = _isTriviaActionTriggered

    fun setCurrentUser(currentUser: CurrentUser) {
        _currentUser.postValue(currentUser.getUser())
    }

    fun setCurrentQuizz(name: String, quizz: CurrentQuizz) {
        _isLoading.postValue(true)
        _isTriviaActionTriggered.postValue(true)

        quizzRepository.getQuiz(
            name,
            object : SuccessCallback<Quizz> {
                override fun onComplete(result: Quizz) {
                    _currentQuizz.postValue(result)
                    quizz.setQuizz(result)
                    _isLoading.postValue(false)
                }
            },
            object : ErrorCallback {
                override fun onError(e: Exception) {
                    Log.e("MainScreenVM", "Error loading quiz: ${e.message}")
                    _currentQuizz.postValue(null)
                    _isLoading.postValue(false)
                    _isTriviaActionTriggered.postValue(false)
                }
            }
        )
    }

    fun resetTriviaActionTrigger() {
        _isTriviaActionTriggered.postValue(false)
    }
}

