package com.example.todo_compose.ui.component.content

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.todo_compose.util.Action
import kotlinx.coroutines.launch

@Composable
fun DisplaySnackbar(
    scaffoldState: ScaffoldState,
    handleDatabaseAction: () -> Unit,
    title: String,
    action: Action,
    onSnackbarActionClick: (Action) -> Unit
) {
    handleDatabaseAction()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action){
        if(action != Action.NO_ACTION) {
            scope.launch {
                val message = if(action == Action.DELETE_ALL) "Delete all task" else "$action: $title"
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = if(action == Action.DELETE) "UNDO" else "OK"
                )
                onSnackbarAction(
                    action = action,
                    snackbarResult = snackbarResult,
                    onSnackbarActionClick = onSnackbarActionClick
                )
            }
        }
    }
}

fun onSnackbarAction(
    action: Action,
    snackbarResult: SnackbarResult,
    onSnackbarActionClick: (Action) -> Unit
) {
    if(snackbarResult == SnackbarResult.ActionPerformed && action == Action.DELETE){
        onSnackbarActionClick(Action.UNDO)
    }
}