package com.mike.wishlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MVVM_WishList: ViewModel() {

    //private val _wishList: MutableStateFlow<List<WishList>> = MutableStateFlow<List<WishList>>(emptyList<WishList>())
    private val _wishList: MutableStateFlow<List<WishList>> = MutableStateFlow<List<WishList>>(
        DummyWish.wishList)
    val wishList: StateFlow<List<WishList>> = _wishList


    fun addWish(wishList: WishList) {
        if (_wishList.value == null) {
            _wishList.value = emptyList<WishList>()
        }
        _wishList.value = _wishList.value?.plus(wishList)!!
    }

    fun removeWish(wishList: WishList) {
        if (_wishList.value == null) {
            _wishList.value = emptyList<WishList>()
        }
        _wishList.value = _wishList.value?.filter { it != wishList }!!
    }

    fun updateWish(wishList: WishList) {
        if (_wishList.value == null) {
            _wishList.value = emptyList<WishList>()
        }
        _wishList.value = _wishList.value.map { if (it.id == wishList.id) wishList else it }
    }

    fun findById(id: Int): WishList? {
        if (_wishList.value == null) {
            _wishList.value = emptyList<WishList>()
        }
        return _wishList.value.find { it.id == id }
    }


}