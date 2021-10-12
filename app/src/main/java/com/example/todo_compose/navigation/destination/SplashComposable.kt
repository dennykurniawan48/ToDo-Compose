package com.example.todo_compose.navigation.destination

import android.window.SplashScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo_compose.ui.component.Splash.SplashScreen
import com.example.todo_compose.ui.screen.ListScreen
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.Constant
import com.example.todo_compose.viewmodel.SharedViewModel

fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
){
    composable(
        route = Constant.SPLASH_SCREEN
    ){
        SplashScreen(navigateToListScreen)
    }
}