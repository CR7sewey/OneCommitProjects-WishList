package com.mike.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mike.wishlist.ui.theme.WishListTheme

class MainActivity : ComponentActivity() {
    private val viewModelWish by viewModels<MVVM_WishList>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WishListTheme {
                var navHostController = rememberNavController()
                var title by remember { mutableStateOf("Wish List") }
                var showIcon by remember { mutableStateOf(false) }

                var wishListTitle = stringResource(R.string.home)
                var addWishTitle = stringResource(R.string.add)
                var editWishTitle = stringResource(R.string.edit)

                fun changeBackArrow(screen: String) {
                    when (screen) {
                        wishListTitle -> {
                            title = wishListTitle
                            showIcon = false
                        }

                        addWishTitle -> {
                            title = addWishTitle
                            showIcon = true
                        }

                        editWishTitle -> {
                            title = editWishTitle
                            showIcon = true
                        }
                    }
                }


                Scaffold(
                    modifier = Modifier.fillMaxSize() ,
                    topBar = { TopBar(title, showIcon = showIcon, navHostController, changeBackArrow = {
                        changeBackArrow("Wish List")
                    })},
                    floatingActionButton = { FloatingButton(
                        changeBackArrow = { screen: String ->
                            changeBackArrow(screen)
                        },
                        navHostController, title,
                        viewModelWish = viewModelWish,
                        ) },
                    floatingActionButtonPosition = FabPosition.EndOverlay,

                ) { innerPadding ->
                    App(

                        navHostController = navHostController,
                        changeBackArrow = { screen: String ->
                            changeBackArrow(screen)
                        },
                        viewModelWish = viewModelWish,
                        modifier = Modifier
                    )

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = "Wish List", showIcon: Boolean = false, navHostController: NavHostController, changeBackArrow: () -> Unit,  modifier: Modifier = Modifier) {
    TopAppBar(
        title = {


                Text(text = title,
                    modifier = Modifier.padding(start = 4.dp).heightIn(max = 24.dp),)

                },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = colorResource(R.color.rose_500),
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier,
        navigationIcon = {
            if (showIcon) {
                IconButton(onClick = {
                    navHostController.navigateUp() // go back i the naviagtion hierarchy
                    changeBackArrow.invoke()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Add",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }

    )
    
}

@Composable
fun FloatingButton(changeBackArrow: (String) -> Unit, navHostController: NavHostController, title: String, viewModelWish: MVVM_WishList? = null, modifier: Modifier = Modifier) {

    if (title.contains("Add")) {
        return
    }
    var wishListTitle = stringResource(R.string.home)
    var addWishTitle = stringResource(R.string.add)
    var editWishTitle = stringResource(R.string.edit)

    FloatingActionButton(
        onClick = {
            if (title.contains("Edit")) {
                viewModelWish?.removeWish(navHostController.previousBackStackEntry?.savedStateHandle?.get<WishList>("item")!!)
                navHostController.popBackStack()
                changeBackArrow.invoke(wishListTitle)

            }
            else if (title.contains("Wish")) {

                navHostController.navigate(Screen.Add.route)
                changeBackArrow.invoke(addWishTitle)
            }
                  },
        modifier = modifier,
        containerColor = Color.Black,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        if (title.contains("Edit")) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.onPrimary
            )
            return@FloatingActionButton
        }
        if (title.contains("Wish")) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = MaterialTheme.colorScheme.onPrimary
            )        }


    }

}




