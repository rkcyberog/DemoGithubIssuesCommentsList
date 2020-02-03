package com.rahul.gamechangeassignment.di

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room
import com.rahul.gamechangeassignment.data.local.AppDatabase
import com.rahul.gamechangeassignment.data.local.dao.CommentsDao
import com.rahul.gamechangeassignment.data.local.dao.IssuesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "issues.db"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }


    @Provides
    @Singleton
    fun provideIssuesDao(@NonNull appDatabase: AppDatabase): IssuesDao {
        return appDatabase.issuesDao()
    }

    @Provides
    @Singleton
    fun provideCommentsDao(@NonNull appDatabase: AppDatabase): CommentsDao {
        return appDatabase.commentsDao()
    }
}
