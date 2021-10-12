package com.example.todo_compose.ui.component.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo_compose.R
import com.example.todo_compose.ui.theme.ICON_EMPTY
import com.example.todo_compose.ui.theme.MediumGray

@Composable
fun EmptyListContent() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            modifier = Modifier.size(ICON_EMPTY),
            painter = painterResource(id = R.drawable.ic_mood),
            contentDescription = stringResource(id = R.string.empty_task),
            tint = MediumGray
        )
        Text(
            text = stringResource(id = R.string.empty_task),
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

@Preview
@Composable
fun EmptyTaskPreview() {
    EmptyListContent()
}