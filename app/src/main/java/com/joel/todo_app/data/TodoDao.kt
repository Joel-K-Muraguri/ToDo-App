package com.joel.todo_app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//Flow is used to get all the data from the database to be displayed on the screen
//

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM Todo_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM Todo_table WHERE id =:id")
    suspend fun getById(id : Int) : Todo?

    @Query("Select * from Todo_table")
    fun getAllTodos() : Flow<List<Todo>>
}