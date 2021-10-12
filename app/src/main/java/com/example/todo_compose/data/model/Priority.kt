package com.example.todo_compose.data.model

import androidx.compose.ui.graphics.Color
import com.example.todo_compose.ui.theme.HighPriorityColor
import com.example.todo_compose.ui.theme.LowPriorityColor
import com.example.todo_compose.ui.theme.MediumPriorityColor
import com.example.todo_compose.ui.theme.NonePriorityColor

enum class Priority(val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor)
}