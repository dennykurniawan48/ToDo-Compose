package com.example.todo_compose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_compose.data.model.Priority
import com.example.todo_compose.data.model.ToDoTask
import com.example.todo_compose.repository.DataStoreRepository
import com.example.todo_compose.repository.ToDoRepository
import com.example.todo_compose.util.Action
import com.example.todo_compose.util.Constant
import com.example.todo_compose.util.RequestState
import com.example.todo_compose.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
        private val repository: ToDoRepository,
        private val dataStoreRepository: DataStoreRepository
    ): ViewModel() {

    private val _allTask = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTask: StateFlow<RequestState<List<ToDoTask>>> = _allTask

    val searchAppBarState: MutableState<SearchAppBarState> = mutableStateOf(SearchAppBarState.CLOSE)
    val searchAppBarText: MutableState<String> = mutableStateOf("")

    fun getAllTask(){
        try {
            _allTask.value = RequestState.Loading
            viewModelScope.launch {
                repository.getAllTask.collect {
                    _allTask.value = RequestState.Success(it)
                }
            }
        }catch (e: Exception){
            _allTask.value = RequestState.Error(e)
        }
    }

    val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    fun getSelectedTask(taskId: Int){
        viewModelScope.launch {
            repository.getSelectedTask(taskId).collect {
                _selectedTask.value = it
            }
        }
    }



    private val _searchTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchTasks


    fun getSearchTask(query: String){
        try {
            _searchTasks.value = RequestState.Loading
            viewModelScope.launch {
                repository.getSearchTask(query = query).collect {
                    _searchTasks.value = RequestState.Success(it)
                }
            }
        }catch (e: Exception){
            _searchTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGER
    }

    val id: MutableState<Int> = mutableStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val priority: MutableState<Priority> = mutableStateOf(Priority.LOW)

    fun updateTaskForm(toDoTask: ToDoTask?){
        if(toDoTask != null){
            id.value = toDoTask.id
            title.value = toDoTask.title
            description.value = toDoTask.description
            priority.value = toDoTask.priority
        }else{
            id.value = 0
            title.value = ""
            description.value = ""
            priority.value = Priority.LOW
        }
    }

    fun updateTitle(newTitle: String){
        if(newTitle.length <= Constant.TITLE_TASK_LENGHT){
            title.value = newTitle
        }
    }

    val action = mutableStateOf(Action.NO_ACTION)

    fun validateForm(): Boolean{
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }

    fun insertNewTask(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(ToDoTask(
                title = title.value,
                description = description.value,
                priority = priority.value
            ))
        }
        searchAppBarState.value = SearchAppBarState.CLOSE
    }

    fun updateTask(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            ))
        }
        searchAppBarState.value = SearchAppBarState.CLOSE
    }

    fun deleteTask(){
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                id = id.value,
                title = title.value,
                description = description.value,
                priority = priority.value
            )
            repository.deleteTask(toDoTask)
        }
    }

    fun handleActionToDatabase(action: Action){
        when(action){
            Action.ADD -> insertNewTask()
            Action.UPDATE -> updateTask()
            Action.DELETE -> deleteTask()
            Action.DELETE_ALL -> deleteAllTask()
            Action.UNDO -> insertNewTask()
            else -> {}
        }
        this.action.value = Action.NO_ACTION
    }

    fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTask()
        }
    }

    val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun getSortState(){
        try {
            _sortState.value = RequestState.Loading
            viewModelScope.launch {
                dataStoreRepository.readSortState.map {
                    Priority.valueOf(it)
                }.collect {
                    _sortState.value = RequestState.Success(it)
                }
            }
        }catch (e: Exception){
            _sortState.value = RequestState.Error(e)
        }
    }

    fun persistSortingState(priority: Priority){
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistStateSort(priority = priority)
        }
    }

    val _orderedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val orderedTasks: StateFlow<RequestState<List<ToDoTask>>> = _orderedTasks

    fun getOrderedTask(priority: RequestState<Priority>){
        if(priority is RequestState.Success) {
            try {
                viewModelScope.launch {
                    when (priority.data) {
                        Priority.LOW -> {
                            repository.getAllTaskSortedLow.collect {
                                _orderedTasks.value = RequestState.Success(it)
                            }
                        }
                        Priority.HIGH -> {
                            repository.getAllTaskSortedHigh.collect {
                                _orderedTasks.value = RequestState.Success(it)
                            }
                        }
                        else -> {
                            _orderedTasks.value = _allTask.value
                        }
                    }
                }
            } catch (ex: Exception) {
                _orderedTasks.value = RequestState.Error(ex)
            }
        }else{
            _orderedTasks.value = _allTask.value
        }
    }
}