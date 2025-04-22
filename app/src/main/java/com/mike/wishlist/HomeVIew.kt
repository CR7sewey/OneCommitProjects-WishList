package com.mike.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun HomeView(
    navHostController: NavHostController,
    changeBackArrow: () -> Unit,
    viewModelWish: MVVM_WishList,
    modifier: Modifier = Modifier) {

    val wishList by viewModelWish.wishList.collectAsState()

    Column(
        modifier= modifier.fillMaxSize().padding(8.dp)

    ) {


        LazyColumn {
            items(wishList.reversed()) { current ->
                //val current = wishList[index]
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize().clickable {
                            navHostController.currentBackStackEntry?.savedStateHandle?.set(
                                "item",
                                current)
                            navHostController.navigate(
                                Screen.Update.createRoute(current.id.toString())
                            )
                            changeBackArrow.invoke()

                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )

                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxSize()
                    ) {
                        Text(text = current.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = current.summary)
                    }
                }
            }
        }
    }
    
}