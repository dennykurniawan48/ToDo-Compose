package com.example.todo_compose.navigation.destination

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo_compose.ui.screen.ListScreen
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.Constant
import com.example.todo_compose.viewmodel.SharedViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
){
    composable(
        route = Constant.LIST_SCREEN,
        arguments = listOf(navArgument(Constant.LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ){ navBackStack ->
        val arg = navBackStack.arguments!!.getString(Constant.LIST_ARGUMENT_KEY)
        val action = if(arg != null) Action.valueOf(arg) else Action.NO_ACTION
        LaunchedEffect(key1 = action){
            sharedViewModel.action.value = action
        }
        ListScreen(navigateToTaskScreen = navigateToTaskScreen, sharedViewModel=sharedViewModel)
    }
}