package com.example.todo_compose.repository

import com.example.todo_compose.data.ToDoDao
import com.example.todo_compose.data.model.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    val getAllTask: Flow<List<ToDoTask>> = toDoDao.getAllTask()
    val getAllTaskSortedLow: Flow<List<ToDoTask>> = toDoDao.sortByLowestPriority()
    val getAllTaskSortedHigh: Flow<List<ToDoTask>> = toDoDao.sortByHighestPriority()

    fun getSearchTask(query: String): Flow<List<ToDoTask>> = toDoDao.searchTask(query)
    fun getSelectedTask(id: Int): Flow<ToDoTask> = toDoDao.getSelectedTask(id)

    suspend fun addTask(toDoTask: ToDoTask) = toDoDao.insertTask(toDoTask)
    suspend fun updateTask(toDoTask: ToDoTask) = toDoDao.updateTask(toDoTask)
    suspend fun deleteTask(toDoTask: ToDoTask) = toDoDao.deleteTask(toDoTask)
    suspend fun deleteAllTask() = toDoDao.deleteAllTask()
}