package com.example.todo_compose.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo_compose.data.model.ToDoDatabase
import com.example.todo_compose.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context, ToDoDatabase::class.java, Constant.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(toDoDatabase: ToDoDatabase) = toDoDatabase.toDoDao()
}