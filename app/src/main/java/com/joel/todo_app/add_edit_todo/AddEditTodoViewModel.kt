package com.joel.todo_app.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.todo_app.data.Repository
import com.joel.todo_app.data.Todo
import com.joel.todo_app.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository : Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var todo by mutableStateOf<Todo?>(null)
        private set

     var title by mutableStateOf("")
         private set

    var description by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")!!
        if(todoId != -1){
            viewModelScope.launch {
                repository.getById(todoId)?.let { todo ->  
                  title = todo.title
                  description =  todo.description?: "" // if there's no description we pass another one
                  this@AddEditTodoViewModel.todo = todo
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent){
        when(event){
            is AddEditEvent.OnTitleChange ->{
                title = event.title
            }
            is AddEditEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditEvent.OnAddTodoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()){
                        sendUiEvent(UiEvent.SnackBar(
                            message = "The title cannot be empty"
                        ))
                        return@launch
                    }
                    repository.insert(
                        Todo(
                            id = todo?.id,
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
        }
    }
    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}