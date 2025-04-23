package com.mike.wishlist.localRepository

import com.mike.wishlist.WishList
import com.mike.wishlist.localDB.WishEntity
import kotlinx.coroutines.flow.Flow

interface IWishRepository {

    fun getAll(): Result<Flow<List<WishEntity>>>

    suspend fun insertAll(wishList: List<WishList>)

    suspend fun insertOne(vararg wishEntity: WishList)

    suspend fun update(wishEntity: WishList)

    suspend fun delete(wishEntity: WishList)

    fun getWishById(id: Int): Result<Flow<WishEntity>>
}