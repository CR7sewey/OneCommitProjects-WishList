package com.mike.wishlist

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mike.wishlist.localDB.MyWishDatabase
import com.mike.wishlist.localRepository.IWishRepository
import com.mike.wishlist.localRepository.WishRepository

/*
class WishApplication: Application() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            MyWishDatabase::class.java, "wish_list"
        ).build()
    }
    private val wishDao by lazy {
        db.getWishDao()
    }

    val repository: IWishRepository by lazy {
        WishRepository(db.getWishDao())
    }

}*/

// This is the application class for the WishList app
// It initializes the database and the repository
// It is used to provide a singleton instance of the database and the repository
class WishApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.initDatabase(this)
    }
}

object Graph { // singleton
    lateinit var database: MyWishDatabase

    val wishRepository: IWishRepository by lazy {
        WishRepository(database.getWishDao())
    }

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context,
            MyWishDatabase::class.java, "wish_list"
        ).build()
    }

}