package com.example.todo_compose.ui.screen

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.R
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.ui.component.ListAppBar
import com.example.todo_compose.ui.component.content.DisplaySnackbar
import com.example.todo_compose.ui.component.content.EmptyListContent
import com.example.todo_compose.ui.component.content.ListContent
import com.example.todo_compose.ui.theme.FabBackground
import com.example.todo_compose.util.RequestState
import com.example.todo_compose.util.SearchAppBarState
import com.example.todo_compose.viewmodel.SharedViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
){

    val appBarState by sharedViewModel.searchAppBarState
    val searchText by sharedViewModel.searchAppBarText
    val action by sharedViewModel.action
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        sharedViewModel.getAllTask()
        sharedViewModel.getSortState()
    }

    DisplaySnackbar(
        scaffoldState = scaffoldState,
        handleDatabaseAction = { sharedViewModel.handleActionToDatabase(action = action) },
        title = sharedViewModel.title.value,
        action = action,
        onSnackbarActionClick = {sharedViewModel.action.value = it}
    )

    val allTask by sharedViewModel.allTask.collectAsState()
    val searchedTask by sharedViewModel.searchTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()

    val orderedState by sharedViewModel.orderedTasks.collectAsState()

    LaunchedEffect(key1 = sortState){
        sharedViewModel.getOrderedTask(sortState)
//        println("Calling Sotred Srate for x times")
        if(sortState is RequestState.Success){
            println("Calling Sotred Srate for ${(sortState as RequestState.Success<Priority>).data ?: ""}")
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { ListAppBar(
            sharedViewModel = sharedViewModel,
            appBarState = appBarState,
            text = searchText
        ) },
        content = {
            ListContent(
                tasks = if (appBarState == SearchAppBarState.TRIGGER) searchedTask else allTask,
                orderedtasks = orderedState,
                navigateToTask = navigateToTaskScreen,
                appBarState = appBarState,
                onSwipeDelete = { action, task ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateTaskForm(task)
                }
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        }
    )
}

@Composable
fun ListFab(
    onFabClick: (Int) -> Unit
){
    FloatingActionButton(onClick = {
        onFabClick(-1)
    }, backgroundColor = MaterialTheme.colors.FabBackground) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_btn),
            tint = Color.White
        )
    }
}
