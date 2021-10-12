package com.example.todo_compose.ui.component.content

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.ui.component.PriorityItem
import com.example.todo_compose.ui.theme.PADDING_LARGE
import com.example.todo_compose.ui.theme.PADDING_MEDIUM
import com.example.todo_compose.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityDropdown(
    priority: Priority,
    onPriorityChanged: (Priority) -> Unit
) {

    var expanded by remember{ mutableStateOf(false) }
    val angle: Float by animateFloatAsState(targetValue = if(expanded) 180f else 0f)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            ),
        verticalAlignment = Alignment.CenterVertically
    ){
        Canvas(modifier = Modifier
            .size(PRIORITY_INDICATOR_SIZE)
            .weight(1f)){
            drawCircle(color = priority.color)
        }
        Text(
            modifier = Modifier
                .weight(8f)
                .padding(PADDING_LARGE),
            text = priority.name,
            style = MaterialTheme.typography.subtitle2
        )
        IconButton(onClick = { expanded = true }, modifier = Modifier.weight(1.5f)) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                modifier = Modifier.rotate(angle)
            )
        }
        
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityChanged(Priority.LOW)
            }) {
                PriorityItem(priority = Priority.LOW)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityChanged(Priority.MEDIUM)
            }) {
                PriorityItem(priority = Priority.MEDIUM)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onPriorityChanged(Priority.HIGH)
            }) {
                PriorityItem(priority = Priority.HIGH)
            }
        }
    }    
}

@Preview
@Composable
fun PriorityItemPrev() {
    PriorityDropdown(priority = Priority.HIGH, onPriorityChanged = {})
}