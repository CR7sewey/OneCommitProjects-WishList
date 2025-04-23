package com.mike.wishlist.localDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Query("SELECT * FROM wish")
    fun getAll(): Flow<List<WishEntity>> // // No need to add suspend keyword, because it returns Flow, flows already uses coroutines.

    @Query("SELECT * FROM wish WHERE id = :id")
    fun getWishById(id: Int): Flow<WishEntity>

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertAll(wishList: List<WishEntity>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertOne(vararg wishEntity: WishEntity)

    @Update
    suspend fun update(wishEntity: WishEntity)

    @Delete
    suspend fun delete(wishEntity: WishEntity)
}

/**
 * @Dao
 * abstract class WishDao {
 *
 *     @Query("SELECT * FROM wish")
 *     abstract suspend fun getAll(): List<WishEntity>
 *
 *     @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
 *     abstract suspend fun insertAll(wishList: List<WishEntity>)
 *
 *     @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
 *     abstract suspend fun insertOne(vararg wishEntity: WishEntity)
 *
 *     @Update
 *     abstract suspend fun update(wishEntity: WishEntity)
 *
 *     @Delete
 *     abstract suspend fun delete(wishEntity: WishEntity)
 * }
 */