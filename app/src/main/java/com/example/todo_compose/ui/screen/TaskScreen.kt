package com.example.todo_compose.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.ui.component.appbar.TaskAppBar
import com.example.todo_compose.ui.component.content.PriorityDropdown
import com.example.todo_compose.ui.component.task.TaskContent
import com.example.todo_compose.util.Action
import com.example.todo_compose.viewmodel.SharedViewModel

@Composable
fun TaskScreen(
    toDoTask: ToDoTask?,
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val title by sharedViewModel.title
    val description by sharedViewModel.description
    val priority by sharedViewModel.priority

    val context = LocalContext.current

    BackHandler(onBack = { navigateToListScreen(Action.NO_ACTION) })

    Scaffold(
        topBar = {
            TaskAppBar(
                navigateToListScreen = { action ->
                     if(action == Action.NO_ACTION || sharedViewModel.validateForm()){
                         navigateToListScreen(action)
                     }else{
                         showToast(context)
                     }
                },
                toDoTask = toDoTask
            )
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = { sharedViewModel.updateTitle(it) },
                description = description,
                onDescriptionChange = { sharedViewModel.description.value = it },
                priority = priority,
                onPriorityChange = { sharedViewModel.priority.value = it }
            )
        }
    )
}

fun showToast(context: Context) {
    Toast.makeText(context, "Form is empty", Toast.LENGTH_SHORT).show()
}

@Composable
fun HandleBack(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPress: () -> Unit
) {
    val currentOnBackPress by rememberUpdatedState(newValue = onBackPress)

    val backCallback = remember {
        object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                currentOnBackPress()
            }

        }
    }

    DisposableEffect(key1 = backDispatcher){
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}