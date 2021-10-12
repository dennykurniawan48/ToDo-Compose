package com.example.todo_compose.ui.component.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.ui.theme.*
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.RequestState
import com.example.todo_compose.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    orderedtasks: RequestState<List<ToDoTask>>,
    onSwipeDelete: (Action, ToDoTask) -> Unit,
    appBarState: SearchAppBarState,
    navigateToTask: (Int) -> Unit
) {
    if(appBarState == SearchAppBarState.TRIGGER) {
        if (tasks is RequestState.Success) {
            if (tasks.data.isEmpty()) {
                EmptyListContent()
            } else {
                HandleItem(
                    tasks = tasks.data,
                    navigateToTask = navigateToTask,
                    onSwipeDelete = onSwipeDelete
                )
            }
        }
    }else {
        if (orderedtasks is RequestState.Success) {
            if (orderedtasks.data.isEmpty()) {
                EmptyListContent()
            } else {
                HandleItem(
                    tasks = orderedtasks.data,
                    navigateToTask = navigateToTask,
                    onSwipeDelete = onSwipeDelete
                )
            }
        }else{
            if (tasks is RequestState.Success) {
                if (tasks.data.isEmpty()) {
                    EmptyListContent()
                } else {
                    HandleItem(
                        tasks = tasks.data,
                        navigateToTask = navigateToTask,
                        onSwipeDelete = onSwipeDelete
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HandleItem(
    tasks: List<ToDoTask>,
    onSwipeDelete: (Action, ToDoTask) -> Unit,
    navigateToTask: (Int) -> Unit
) {
    LazyColumn {
        items(
            items = tasks,
            key = { task -> task.id }
        ) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismiss = dismissState.isDismissed(DismissDirection.EndToStart)
            if(isDismiss && dismissDirection == DismissDirection.EndToStart){
                val scope = rememberCoroutineScope()
                scope.launch {
                    delay(300)
                    onSwipeDelete(Action.DELETE, task)
                }
            }
            val degress by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f)

            var itemAppear by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(key1 = true){
                itemAppear = true
            }

            AnimatedVisibility(
                visible = itemAppear && !isDismiss,
                enter = expandVertically(
                    animationSpec = tween(300)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 300)
                )
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    background = { SwipeDelete(degrees = degress) },
                    dismissThresholds = {FractionalThreshold(0.2f)},
                    dismissContent = {
                        ListItem(
                            toDoTask = task,
                            navigateToTask = navigateToTask
                        )
                    }
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ListItem(
    toDoTask: ToDoTask,
    navigateToTask: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.itemBackground,
        shape = RectangleShape,
        elevation = TASK_ELEVATION,
        onClick = { navigateToTask(toDoTask.id)}
    ) {
        Column(modifier = Modifier
            .padding(PADDING_LARGE)
            .fillMaxWidth()) {
            Row{
               Text(
                   text = toDoTask.title,
                   color = MaterialTheme.colors.itemTextColor,
                   style = MaterialTheme.typography.h5,
                   fontWeight = FontWeight.Bold,
                   modifier = Modifier.weight(8f)
               ) 
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Text(
                text = toDoTask.description,
                color = MaterialTheme.colors.itemTextColor,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
fun TaskItemPreview() {
    ListItem(toDoTask = ToDoTask(id = 1, "Denny", "Nama saya Denny", Priority.HIGH), navigateToTask = {})
}