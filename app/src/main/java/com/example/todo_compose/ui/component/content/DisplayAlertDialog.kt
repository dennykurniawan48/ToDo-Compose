package com.example.todo_compose.ui.component.content

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    title: String,
    message: String,
    openDialog: Boolean,
    onCloseDialog: () -> Unit,
    onConfirmDialog: () -> Unit
) {
    if(openDialog){
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(onClick = {
                    onConfirmDialog()
                    onCloseDialog()
                }) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    onCloseDialog()
                }) {
                    Text(text = "No")
                }
            },
            onDismissRequest = {
              //  onCloseDialog()
            }
        )
    }
}