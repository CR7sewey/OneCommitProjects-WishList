package com.mike.wishlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.mike.wishlist.localDB.WishDao
import com.mike.wishlist.localDB.WishEntity
import com.mike.wishlist.localRepository.IWishRepository
import com.mike.wishlist.localRepository.WishRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MVVM_WishList(private val wishRepository: IWishRepository = Graph.wishRepository): ViewModel() {

    //private val _wishList: MutableStateFlow<List<WishList>> = MutableStateFlow<List<WishList>>(emptyList<WishList>())
    private val _wishList: MutableStateFlow<List<WishList>> = MutableStateFlow<List<WishList>>(
        emptyList())
    val wishList: StateFlow<List<WishList>> = _wishList

    init {
        // Load the initial data from the database
        getAll()
    }


    fun addWish(wishList: WishList) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_wishList.value == null) {
                _wishList.value = emptyList<WishList>()
            }
            _wishList.value = _wishList.value?.plus(wishList)!!
            wishRepository.insertOne(
                wishList
            )
        }

    }

    fun removeWish(wishList: WishList) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_wishList.value == null) {
                _wishList.value = emptyList<WishList>()
            }
            _wishList.value = _wishList.value?.filter { it != wishList }!!

            wishRepository.delete(
                wishList)
        }

    }

    fun updateWish(wishList: WishList) {
        viewModelScope.launch(Dispatchers.IO) {
            if (_wishList.value == null) {
                _wishList.value = emptyList<WishList>()
            }
            _wishList.value = _wishList.value.map { if (it.id == wishList.id) wishList else it }
            wishRepository.update(
                wishList
            )
        }
    }

    fun findById(id: Int): Flow<WishEntity>? {
        if (_wishList.value == null) {
            _wishList.value = emptyList<WishList>()
        }
        return wishRepository.getWishById(id).getOrNull()
    }

    private fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val wishListF = wishRepository.getAll()
            if (wishListF.isFailure) {
                // Handle the error
                return@launch
            }
            // Collect the flow and update the mutable state
            wishListF.getOrNull()?.collect { wishList ->
                val wishListItems = wishList.map {
                    WishList(
                        id = it.id,
                        title = it.title,
                        summary = it.summary
                    )
                }
                _wishList.value = wishListItems
            } ?: emptyList<WishList>() as List<WishList>
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application: WishApplication = checkNotNull(extras[APPLICATION_KEY]) as WishApplication
                // Get the Application object from extras
                val repo = Graph.wishRepository
                return MVVM_WishList(wishRepository = repo) as T

            }
        }
    }


}