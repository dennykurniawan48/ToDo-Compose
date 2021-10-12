package com.example.todo_compose.navigation

import androidx.navigation.NavHostController
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.Constant

class Screens(navController: NavHostController) {

    val splash: () -> Unit = {
        navController.navigate("list/${Action.NO_ACTION.name}"){
            popUpTo(Constant.LIST_SCREEN){ inclusive = true }
        }
    }

    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            popUpTo(Constant.LIST_SCREEN){ inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}