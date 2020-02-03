package com.rahul.gamechangeassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahul.gamechangeassignment.data.IssuesResponse
import io.reactivex.Single

@Dao
interface IssuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllIssues(issues: List<IssuesResponse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssues(issuesResponse: IssuesResponse)


    @Query("SELECT * FROM IssuesResponse ORDER BY number Desc")
    fun getAllIssues(): Single<List<IssuesResponse>>



}