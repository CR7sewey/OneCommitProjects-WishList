package com.mike.wishlist

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberBottomSheetScaffoldState

@Composable
fun AddOne(item: WishList? = null, viewModelWish: MVVM_WishList, navHostController: NavHostController, changeBackArrow: () -> Unit, changeSnackMessage: (String) -> Unit, modifier: Modifier = Modifier) {

    var title by remember { mutableStateOf(item?.title ?: "") }
    var summary by remember { mutableStateOf(item?.summary ?: "") }
    var lastElement = viewModelWish.wishList.collectAsState().value.lastOrNull()


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        TextInput(
            value = title,
            onValueChange = {
                title = it
            },
            label = "Title",
            modifier = modifier
                .padding(16.dp)
        )
        TextInput(
            value = summary,
            onValueChange = {
                summary = it
            },
            label = "Summary",
            modifier = modifier
                .padding(16.dp)
        )
        Button(
            onClick = {
                if (title.isEmpty() || summary.isEmpty()) {
                    Toast.makeText(navHostController.context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (item != null) {
                    viewModelWish.updateWish(
                        WishList(
                            id = item.id,
                            title = title.trim(),
                            summary = summary.trim()
                        )
                    )
                } else {
                    viewModelWish.addWish(
                        WishList(
                            id = lastElement?.id?.plus(1) ?: viewModelWish.wishList.value.size,
                            title = title.trim(),
                            summary = summary.trim()
                        )
                    )
                }
                navHostController.popBackStack()
                changeBackArrow.invoke()
                changeSnackMessage.invoke("Wish ${if (item != null) "updated" else "added"} successfully")
            },
            modifier = modifier
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.purple_500),
                contentColor = colorResource(id = R.color.white)
            ),
        ) {
            Text(text = if (item != null) "Update" else "Add")
        }

    }

}


@Composable
fun TextInput(value: String = "", onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .focusRequester(focusRequester),
        value = value,
        onValueChange = { it -> onValueChange.invoke(it)
        },
        placeholder = { Text("Enter value") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusRequester.freeFocus()
                focusManager.clearFocus()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorResource(id = R.color.purple_500),
            unfocusedIndicatorColor = colorResource(id = R.color.rose_500),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        label = { Text(label) },
    )

}