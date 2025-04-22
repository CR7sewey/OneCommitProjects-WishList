package com.mike.wishlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WishList(
    val id: Int,
    val title: String,
    val summary: String,
): Parcelable


object DummyWish {
    var wishList = listOf<WishList>(
        WishList(1, "Wish 1", "Summary 1"),
        WishList(2, "Wish 2", "Summary 2"),
        WishList(3, "Wish 3", "Summary 3"),
        WishList(4, "Wish 4", "Summary 4"),
        WishList(5, "Wish 5", "Summary 5 555555555555555555555555555555555555555555555555555555555 555555555555555555555555555555"),
        WishList(6, "Wish 6", "Summary 6"),
        WishList(7, "Wish 1", "Summary 1"),
        WishList(8, "Wish 2", "Summary 2"),
        WishList(9, "Wish 3", "Summary 3"),
        WishList(10, "Wish 4", "Summary 4"),
        WishList(11, "Wish 5", "Summary 5 555555555555555555555555555555555555555555555555555555555 555555555555555555555555555555"),
        WishList(12, "Wish 6", "Summary 6"),
    )
}
