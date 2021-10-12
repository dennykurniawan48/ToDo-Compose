package com.example.todo_compose.navigation.destination

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo_compose.ui.component.appbar.TaskAppBar
import com.example.todo_compose.ui.screen.TaskScreen
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.Constant
import com.example.todo_compose.viewmodel.SharedViewModel

fun NavGraphBuilder.taskComposable(
    sharedViewModel: SharedViewModel,
    navigateToListScreen: (Action) -> Unit
){
    composable(
        route = Constant.TASK_SCREEN,
        arguments = listOf(navArgument(Constant.TASK_ARGUMENT_KEY){
            type = NavType.IntType
        })
    ){ navBackStackEntry ->
       val taskId = navBackStackEntry.arguments!!.getInt(Constant.TASK_ARGUMENT_KEY)

        LaunchedEffect(key1 = taskId){
            sharedViewModel.getSelectedTask(taskId)
        }
        val selectedtask by sharedViewModel.selectedTask.collectAsState()

        Log.d("SelectedTask", selectedtask.toString())

        LaunchedEffect(key1 = selectedtask){
            if(selectedtask != null || taskId == -1) {
                sharedViewModel.updateTaskForm(selectedtask)
            }
        }

        TaskScreen(navigateToListScreen = navigateToListScreen, toDoTask = selectedtask, sharedViewModel = sharedViewModel)
    }
}