package com.example.todo_compose.ui.component.appbar

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.ui.theme.topAppBarBackgroundColor
import com.example.todo_compose.ui.theme.topAppBarContentColor
import com.example.todo_compose.util.Action

@Composable
fun TaskAppBar(
    toDoTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit
) {
    if(toDoTask!=null) {
        ExistingTaskAppBar(toDoTask = toDoTask, navigateToList = navigateToListScreen)
    }else{
        AddTaskAppBar(navigateToListScreen = navigateToListScreen)
    }
}

@Composable
fun AddTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(navigateToListScreen = navigateToListScreen)
        },
        title = {
            Text(text = "Add New Task", color = MaterialTheme.colors.topAppBarContentColor)
        },
        actions = {
            AddtaskAction(navigateToListScreen = navigateToListScreen)
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
fun BackAction(
    navigateToListScreen: (Action) -> Unit
) {
    IconButton(onClick = { navigateToListScreen(Action.NO_ACTION) }) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back to list")
    }
}

@Composable
fun AddtaskAction(
    navigateToListScreen: (Action) -> Unit
) {
    IconButton(onClick = { navigateToListScreen(Action.ADD) }) {
        Icon(imageVector = Icons.Filled.Check, contentDescription = "Back to list")
    }
}

@Preview
@Composable
fun AddTaskAppBarPreview() {
    AddTaskAppBar({})
}