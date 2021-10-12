package com.example.todo_compose.ui.component.Splash

import android.window.SplashScreen
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import com.example.todo_compose.R
import com.example.todo_compose.ui.theme.LOGO_SIZE
import com.example.todo_compose.ui.theme.splashScreenBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navigateToListScreen: () -> Unit
) {

    var startAnimation by remember { mutableStateOf(false) }
    val offsetAnim by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(1000)
    )
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000)
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
        delay(3000L)
        navigateToListScreen()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.splashScreenBackground), contentAlignment = Alignment.Center){
        Image(modifier = Modifier.size(LOGO_SIZE).offset(y = offsetAnim).alpha(alphaAnim),painter = painterResource(
            id = if(isSystemInDarkTheme()) R.drawable.logo_dark else R.drawable.logo_light),
            contentDescription = stringResource(
                id = R.string.app_logo
            )
        )
    }
}