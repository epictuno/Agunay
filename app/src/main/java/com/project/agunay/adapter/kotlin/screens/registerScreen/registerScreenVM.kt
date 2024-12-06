package com.project.agunay.adapter.kotlin.screens.registerScreen

import android.R.attr
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.firebase.FirebaseUserRepository
import com.project.agunay.application.repository.UserRepository
import com.project.agunay.domain.User


class registerScreenVM(
    //private val userRepository: UserRepository = FirebaseUserRepository()
) : ViewModel() {
/*
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun createUser(email: String, password: String, username: String) {
        val user = User(email, password, username)
        userRepository.createUser(
            User(email, password, username)
        ) { user: User? ->
            if (user != null) {
                _currentUser.postValue(user)
                Log.d("CreateUser", "Usuario creado: " + user.username)
            } else {
                Log.e("CreateUser", "Error al crear el usuario")
            }
        }
    }*/
}
