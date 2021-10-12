package com.example.todo_compose.ui.component.appbar

import android.app.Notification
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.ui.component.content.DisplayAlertDialog
import com.example.todo_compose.ui.theme.topAppBarBackgroundColor
import com.example.todo_compose.ui.theme.topAppBarContentColor
import com.example.todo_compose.util.Action
import com.example.todo_compose.R
@Composable
fun ExistingTaskAppBar(
    toDoTask: ToDoTask,
    navigateToList: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(navigateToList = navigateToList)
        },
        title = {
            Text(
                text = toDoTask.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        actions = {
            ExistingTaskAppBarAction(selectedTask = toDoTask, navigateToList = navigateToList)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun CloseAction(
    navigateToList: (Action) -> Unit
) {
    IconButton(onClick = { navigateToList(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    navigateToList: (Action) -> Unit
) {
    IconButton(onClick = { navigateToList(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Update icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    navigateToList: () -> Unit
) {
    IconButton(onClick = { navigateToList() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun ExistingTaskAppBarAction(
    selectedTask: ToDoTask,
    navigateToList: (Action) -> Unit
) {
    var openDialog by remember { mutableStateOf(false)}
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_task, selectedTask.title),
        message = stringResource(id = R.string.confirm_delete_task),
        openDialog = openDialog,
        onCloseDialog = { openDialog = false },
        onConfirmDialog = { navigateToList(Action.DELETE) }
    )
        DeleteAction(navigateToList = {
            openDialog = true
        })
        UpdateAction(navigateToList = navigateToList)
}

@Preview
@Composable
fun ExistingTaskBarPreview() {
    ExistingTaskAppBar(toDoTask = ToDoTask(1, "Denny", "Hello", Priority.HIGH), {})
}