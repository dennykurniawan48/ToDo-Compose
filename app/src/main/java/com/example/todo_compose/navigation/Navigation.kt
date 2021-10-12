package com.example.todo_compose.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todo_compose.navigation.destination.listComposable
import com.example.todo_compose.navigation.destination.splashComposable
import com.example.todo_compose.navigation.destination.taskComposable
import com.example.todo_compose.util.Constant
import com.example.todo_compose.viewmodel.SharedViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
){
    val screen = remember(navController){
        Screens(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Constant.SPLASH_SCREEN
    ){
        splashComposable(navigateToListScreen = screen.splash)
        listComposable(navigateToTaskScreen = screen.task, sharedViewModel = sharedViewModel)
        taskComposable(navigateToListScreen = screen.list, sharedViewModel = sharedViewModel)
    }

}