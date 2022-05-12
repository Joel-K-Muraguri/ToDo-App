package com.joel.todo_app.di

import android.app.Application
import androidx.room.Room
import com.joel.todo_app.data.Repository
import com.joel.todo_app.data.RepositoryImplementation
import com.joel.todo_app.data.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton

    // Function that provides the Database functionality
    fun provideDataBase(app: Application) : TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "TODO_DATABASE"
        ).build()
    }

    @Provides
    @Singleton
    // Function that deals with the repository
    fun provideTodoRepository(db:TodoDatabase): Repository {
        return RepositoryImplementation(db.dao)
    }

}