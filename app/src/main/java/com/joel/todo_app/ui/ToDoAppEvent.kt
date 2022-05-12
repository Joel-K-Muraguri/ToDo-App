package com.joel.todo_app.ui

import androidx.compose.runtime.State
import com.joel.todo_app.data.Todo

sealed class ToDoAppEvent{
    // AddTodo
    object OnAddTodoClick : ToDoAppEvent()

    // Update a ttodo
    data class OnTodoChange(val todo: Todo, val isDone: Boolean) : ToDoAppEvent()

    // Navigate to specific ttodo to access and edit a ttodo
    data class OnTodoClick(val todo: Todo) : ToDoAppEvent()

    // Undo Delete
    object UndoDeleteClick: ToDoAppEvent()

    // Delete Todo
    data class OnDeleteTodoClick(val todo: Todo) : ToDoAppEvent()

    data class OnDeleteAllTodosClick(val todo: Todo) : ToDoAppEvent()

}
