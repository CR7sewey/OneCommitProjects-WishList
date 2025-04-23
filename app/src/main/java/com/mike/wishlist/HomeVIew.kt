package com.mike.wishlist

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

@OptIn(ExperimentalMaterial3Api::class)
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
            items(wishList.reversed(), key = { wish -> wish.id }) { current ->
                //val current = wishList[index]
                val color by animateColorAsState(
                    targetValue = Color.Transparent
                    ,
                    label = ""
                )
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { it ->
                            viewModelWish.removeWish(current)
                        true
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent =
                    {


                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color).padding(horizontal = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.align(Alignment.CenterEnd).padding(4.dp)
                            )
                            /**
                             *   imageVector = Icons.Default.Delete,
                             *                                 contentDescription = null,
                             *                                 color = Color.White,
                             *                                 modifier = Modifier
                             *                                     .padding(16.dp)
                             */
                        }


                    },

                ) {

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
}