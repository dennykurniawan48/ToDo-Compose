package com.example.todo_compose.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.ui.theme.PADDING_LARGE
import com.example.todo_compose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todo_compose.ui.theme.Typography

@Composable
fun PriorityItem(priority: Priority) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Canvas(modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)){
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier.padding(start = PADDING_LARGE),
            text = priority.name,
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}