package com.project.agunay.adapter.kotlin.configuration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.project.agunay.domain.User

class CurrentUser {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun setUser(user: User) {
        _currentUser.value = user
    }

    fun getUser(): User? {
        return currentUser.value
    }
}