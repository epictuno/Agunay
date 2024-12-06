package com.project.agunay.adapter.kotlin.screens.registerScreen

import android.R.attr
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.firebase.ErrorCallback
import com.project.agunay.adapter.firebase.FirebaseUserRepository
import com.project.agunay.adapter.firebase.SuccessCallback
import com.project.agunay.application.repository.UserRepository
import com.project.agunay.domain.User


class registerScreenVM(
    private val userRepository: UserRepository = FirebaseUserRepository()
) : ViewModel() {

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun createUser(email: String, password: String, username: String) {
        _isLoading.postValue(true)
        val user = User(email, password, username)
        userRepository.createUser(user, object : SuccessCallback<User> {
            override fun onComplete(result: User?) {
                _isLoading.postValue(false)
                if (result != null) {

                    _currentUser.postValue(result)

                    Log.d("CreateUser", "Usuario creado: " + result.username)
                } else {
                    _isLoading.postValue(false)
                    _error.postValue("Error al crear el usuario")
                    Log.e("CreateUser", "Error al crear el usuario")
                }
            }
        }, object : ErrorCallback {
            override fun onError(e: Exception) {
                _error.postValue(e.message)
                Log.e("CreateUser", "Error: ${e.message}")
            }
        })
    }
}
