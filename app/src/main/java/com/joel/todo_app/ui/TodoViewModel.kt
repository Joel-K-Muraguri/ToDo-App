package com.joel.todo_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.todo_app.data.Repository
import com.joel.todo_app.data.Todo
import com.joel.todo_app.util.Routes
import com.joel.todo_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val todo = repository.getAllTodos()

    // Used to get all the actions from the sealed UiEvent class
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    // Is used to cache the deleted ttodo and upon click of UNDO action
    // on the Snackbar the Ttodo is restored
    private var deletedTodo: Todo? = null


    fun onEvents(event: ToDoAppEvent){
        when(event){
            is ToDoAppEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.TODO_EDIT_ADD))
            }
            is ToDoAppEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.SnackBar(
                        message = "Deleted TODO",
                        action = "UNDO"
                    ))
                }
            }
            is ToDoAppEvent.OnTodoChange -> {
                viewModelScope.launch {
                    repository.insert(event.todo.copy(
                        isDone = event.isDone
                    ))
                }
            }
            is ToDoAppEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(
                    Routes.TODO_EDIT_ADD + "?todoId = ${event.todo.id}"))
            }

            is ToDoAppEvent.UndoDeleteClick ->{
                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        repository.insert(todo)
                    }
                }
            }else -> Unit
        }
    }
    private fun sendUiEvent(event: UiEvent){
       viewModelScope.launch {
           _uiEvent.send(event)
       }
    }
}