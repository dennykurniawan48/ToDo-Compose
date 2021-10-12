package com.example.todo_compose.ui.component.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.ui.component.PriorityItem
import com.example.todo_compose.ui.component.content.PriorityDropdown
import com.example.todo_compose.ui.theme.PADDING_LARGE
import com.example.todo_compose.ui.theme.PADDING_MEDIUM

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    Column(modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxSize()
        .padding(
            PADDING_LARGE
        )) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            maxLines = 1,
            textStyle = MaterialTheme.typography.body1
        )
        Divider(modifier = Modifier.padding(PADDING_MEDIUM))
        PriorityDropdown(
            priority = priority,
            onPriorityChanged = onPriorityChange
        )
        Divider(modifier = Modifier.padding(PADDING_MEDIUM))
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun TaskContentPrev() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.HIGH,
        onPriorityChange = {}
    )
}