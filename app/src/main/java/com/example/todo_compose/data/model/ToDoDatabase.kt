package com.example.todo_compose.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todo_compose.data.ToDoDao

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDatabase: RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}