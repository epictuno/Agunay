package com.project.agunay.adapter.kotlin.screens.loginScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.firebase.ErrorCallback
import com.project.agunay.adapter.firebase.FirebaseUserRepository
import com.project.agunay.adapter.firebase.SuccessCallback
import com.project.agunay.application.repository.UserRepository
import com.project.agunay.domain.User

class LoginScreenVM(
    private val userRepository: UserRepository = FirebaseUserRepository()
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loginWithEmail(email: String, password: String) {
        _isLoading.postValue(true)
        userRepository.login(email, password, object : SuccessCallback<User> {
            override fun onComplete(result: User?) {
                _isLoading.postValue(false)
                if (result != null) {
                    _currentUser.postValue(result)
                    Log.d("Login", "Usuario logueado: " + result.email)
                } else {
                    _error.postValue("Error al iniciar sesión")
                    Log.e("Login", "Error al iniciar sesión")
                }
            }
        }, object : ErrorCallback {
            override fun onError(e: Exception) {
                _isLoading.postValue(false)
                _error.postValue(e.message)
                Log.e("Login", "Error: ${e.message}")
            }
        })
    }

    fun loginWithUsername(username: String, password: String) {
        _isLoading.postValue(true)
        userRepository.getUserByUsername(username, object : SuccessCallback<User> {
            override fun onComplete(result: User?) {
                if (result != null) {
                    val email = result.email
                    loginWithEmail(email, password)
                } else {
                    _isLoading.postValue(false)
                    _error.postValue(username+ " no encontrado")
                }
            }
        }, ErrorCallback { error: java.lang.Exception ->
            println(error.message)
        });
    }
}