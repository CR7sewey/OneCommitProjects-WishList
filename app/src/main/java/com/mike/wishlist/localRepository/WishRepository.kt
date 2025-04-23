package com.mike.wishlist.localRepository

import androidx.compose.runtime.collectAsState
import com.mike.wishlist.WishList
import com.mike.wishlist.localDB.WishDao
import com.mike.wishlist.localDB.WishEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) : IWishRepository {

    override fun getAll(): Result<Flow<List<WishEntity>>> {
        return try {
            // Get all the wish list items from the database
            val wishListFlow = wishDao.getAll()
            /*val wishListMutable = MutableStateFlow<List<WishList>>(emptyList())
            // Collect the flow and update the mutable state
            wishListFlow
                .flowOn(Dispatchers.IO)
                .collect { wishList ->
                    val wishListItems = wishList.map {
                        WishList(
                            id = it.id,
                            title = it.title,
                            summary = it.summary
                        )
                    }
                    wishListMutable.update { wishListItems }
                }*/
            // Return the result as a success
            Result.success(wishListFlow)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getWishById(id: Int): Result<Flow<WishEntity>> {
        return try {
            // Get the wish list item by id from the database
            val wishListFlow = wishDao.getWishById(id)
            // Return the result as a success
            Result.success(wishListFlow)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertAll(wishList: List<WishList>) {
        val wishEntityList = wishList.map {
            WishEntity(
                title = it.title,
                summary = it.summary
            )
        }
        wishDao.insertAll(wishEntityList)
    }

    override suspend fun insertOne(vararg wishEntity: WishList) {
        val wishEntityList = wishEntity.map {
            WishEntity(
                title = it.title,
                summary = it.summary
            )
        }
        wishDao.insertOne(*wishEntityList.toTypedArray())
    }

    override suspend fun update(wishEntity: WishList) {
        val wishEntityList = WishEntity(
            id = wishEntity.id,
            title = wishEntity.title,
            summary = wishEntity.summary
        )
        wishDao.update(wishEntityList)
    }

    override suspend fun delete(wishEntity: WishList) {
        val wishEntityList = WishEntity(
            id = wishEntity.id,
            title = wishEntity.title,
            summary = wishEntity.summary
        )
        wishDao.delete(wishEntityList)
    }

}
