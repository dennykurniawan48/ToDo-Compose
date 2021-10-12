package com.example.todo_compose.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.ui.theme.APP_BAR_HEIGHT
import com.example.todo_compose.ui.theme.topAppBarBackgroundColor
import com.example.todo_compose.ui.theme.topAppBarContentColor
import com.example.todo_compose.util.SearchAppBarState
import com.example.todo_compose.viewmodel.SharedViewModel
import com.example.todo_compose.R
import com.example.todo_compose.util.Action

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    appBarState: SearchAppBarState,
    text: String
) {
    when(appBarState){
        SearchAppBarState.CLOSE -> ListDefaultAppBar(
            onSearchClick = { sharedViewModel.searchAppBarState.value = SearchAppBarState.OPEN },
            onSortClick = { sharedViewModel.persistSortingState(it) },
            onDeleteAllClick = {
                sharedViewModel.action.value = Action.DELETE_ALL
            }
        )
        else -> ListSearchAppBar(
            text = text,
            onTextChanged = { sharedViewModel.searchAppBarText.value = it },
            onCloseClicked = {
                if(text.isEmpty()){
                    sharedViewModel.searchAppBarState.value = SearchAppBarState.CLOSE
                }
                sharedViewModel.searchAppBarText.value = ""
            },
            onSearchClick = {
                sharedViewModel.getSearchTask(text)
            }
        )
    }

}

@Composable
fun ListDefaultAppBar(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    TopAppBar(
        title = { Text(
            text = stringResource(id = R.string.list_taskbar_title),
            color = MaterialTheme.colors.topAppBarContentColor
        ) },
        actions = {
            ListAppBarAction(onSearchClick = onSearchClick, onSortClick = onSortClick, onDeleteAllClick = onDeleteAllClick)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun ListSearchAppBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChanged(it)
            },
            placeholder = {
                Text(modifier = Modifier.alpha(ContentAlpha.medium),text = "Search", color = Color.White)
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.topAppBarContentColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(modifier = Modifier.alpha(ContentAlpha.disabled),onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onCloseClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.close_icon),
                        tint = MaterialTheme.colors.topAppBarContentColor,
                        modifier = Modifier.alpha(if (text.isNotEmpty()) ContentAlpha.high else ContentAlpha.medium)
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.topAppBarContentColor,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Preview
@Composable
fun ListDefaultAppBarPreview() {
    ListDefaultAppBar(onSearchClick = {}, onSortClick = {}, onDeleteAllClick = {})
}

@Preview
@Composable
fun SearchAppBarPreview() {
    ListSearchAppBar(
        text = "",
        onTextChanged = {},
        onCloseClicked = {},
        onSearchClick = {}
    )
}