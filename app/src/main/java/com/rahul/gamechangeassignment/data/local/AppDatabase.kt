package com.rahul.gamechangeassignment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahul.gamechangeassignment.data.CommentsResponse
import com.rahul.gamechangeassignment.data.local.dao.IssuesDao
import com.rahul.gamechangeassignment.data.IssuesResponse
import com.rahul.gamechangeassignment.data.local.dao.CommentsDao

@Database(entities = [IssuesResponse::class, CommentsResponse::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao

    abstract fun commentsDao(): CommentsDao

}
