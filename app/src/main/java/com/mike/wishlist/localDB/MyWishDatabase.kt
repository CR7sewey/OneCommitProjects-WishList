package com.mike.wishlist.localDB

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WishEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyWishDatabase: RoomDatabase() {
    abstract fun getWishDao(): WishDao
}