package com.joel.todo_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val description: String,
    val isDone: Boolean?
)
