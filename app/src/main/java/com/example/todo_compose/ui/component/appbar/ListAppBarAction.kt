package com.example.todo_compose.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.todo_compose.R
import com.example.todo_compose.R.*
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.ui.component.content.DisplayAlertDialog
import com.example.todo_compose.ui.theme.PADDING_LARGE
import com.example.todo_compose.ui.theme.Typography
import com.example.todo_compose.ui.theme.topAppBarContentColor


@Composable
fun ListAppBarAction(
    onSearchClick: () -> Unit,
    onSortClick: (Priority) -> Unit,
    onDeleteAllClick: () -> Unit
) {
    SearchAppBarAction(onSearchClick = onSearchClick)
    SortAppBarAction(onSortClick = onSortClick)
    MoreAppBarAction(onDeleteAllClick = onDeleteAllClick)
}

@Composable
fun SearchAppBarAction(
    onSearchClick: () -> Unit
) {
    IconButton(onClick = { onSearchClick() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = string.search_task),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun SortAppBarAction(
    onSortClick: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false)}
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = stringResource(id = string.sort_btn),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(onClick = {
            expanded = false
            onSortClick(Priority.LOW)
        }) {
            PriorityItem(priority = Priority.LOW)

        }
        DropdownMenuItem(onClick = { expanded = false
            onSortClick(Priority.HIGH)
        }) {
            PriorityItem(priority = Priority.HIGH)

        }
        DropdownMenuItem(onClick = { expanded = false
            onSortClick(Priority.NONE)
        }) {
            PriorityItem(priority = Priority.NONE)

        }
    }
}

@Composable
fun MoreAppBarAction(
    onDeleteAllClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var openDialog by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_more_vert_24),
            contentDescription = stringResource(id = string.more_action),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(onClick = {
            expanded = false
            openDialog = true
        }) {
            Text(
                modifier = Modifier.padding(start = PADDING_LARGE),
                text = stringResource(id = R.string.delete_all_action),
                style = Typography.subtitle2
            )
        }
    }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_task),
        message = stringResource(
            id = R.string.confirm_delete_task
        ),
        openDialog = openDialog,
        onCloseDialog = { openDialog = false },
        onConfirmDialog = { onDeleteAllClick() }
    )

}