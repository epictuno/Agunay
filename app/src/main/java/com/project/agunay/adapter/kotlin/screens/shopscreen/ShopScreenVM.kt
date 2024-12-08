package com.project.agunay.adapter.kotlin.screens.shopscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.agunay.adapter.firebase.ErrorCallback
import com.project.agunay.adapter.firebase.FirebaseShopItemRepository
import com.project.agunay.adapter.firebase.FirebaseUserRepository
import com.project.agunay.adapter.firebase.SuccessCallback
import com.project.agunay.application.repository.ShopItemRepository
import com.project.agunay.application.repository.UserRepository
import com.project.agunay.domain.ShopItem
import com.project.agunay.domain.User

class ShopScreenVM(
    private val userRepository: UserRepository = FirebaseUserRepository(),
    private val shopItemRepository: ShopItemRepository = FirebaseShopItemRepository()
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val _shopItems = MutableLiveData<List<ShopItem>>()
    val shopItems: LiveData<List<ShopItem>> get() = _shopItems

    init {
        getShopItems()
    }

    fun getShopItems() {
        _isLoading.value = true
        shopItemRepository.getAllShopItems({ items ->
            _shopItems.postValue(items)
            _isLoading.postValue(false)
        }, { error ->
            _error.postValue(error.message)
            _isLoading.postValue(false)
        })
    }

    fun buyShopItem(item: ShopItem, user: User) {
        _isLoading.value = true
        if (user.points >= item.price) {
            user.points -= item.price

            val inventory = user.inventory
            val existingItem = inventory.entries.find { it.key.id == item.id }

            if (existingItem != null) {
                inventory[existingItem.key] = existingItem.value + 1
            } else {
                inventory[item] = 1
            }
            user.inventory = inventory
            userRepository.updateUser(user, {
                _isLoading.value = false
            }, { error ->
                _error.value = error.message
                _isLoading.value = false
            })
        } else {
            _error.value = "Not enough points"
            _isLoading.value = false
        }
    }

    fun cleanErrors() {
        _error.value = null
    }
}