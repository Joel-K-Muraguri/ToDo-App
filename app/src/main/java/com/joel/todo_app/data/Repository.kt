package com.joel.todo_app.data

// This is used as boilerplate code.
// Commonly used as in big apps

import kotlinx.coroutines.flow.Flow

interface Repository  {
    suspend fun insert(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun deleteAll()

    suspend fun getById(id : Int) : Todo?

    fun getAllTodos() : Flow<List<Todo>>
}