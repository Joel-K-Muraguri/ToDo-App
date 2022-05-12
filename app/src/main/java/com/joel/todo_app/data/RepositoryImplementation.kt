package com.joel.todo_app.data

import kotlinx.coroutines.flow.Flow

class RepositoryImplementation(
    private val dao: TodoDao
):Repository {
    override suspend fun insert(todo: Todo) {
        dao.insert(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun getById(id: Int): Todo? {
         return dao.getById(id)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos()
    }
}

// Return keyword is used since data is being retrieved
// either id or flow to display all the data