package com.project.agunay.adapter.kotlin.screens.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.kotlin.configuration.CurrentUser
import com.project.agunay.domain.User

class MainScreenVM: ViewModel() {
    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    fun setCurrentUser(currentUser: CurrentUser) {
        _currentUser.postValue(currentUser.getUser())
    }
}