package com.rahul.gamechangeassignment.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahul.gamechangeassignment.data.CommentsResponse
import io.reactivex.Single

@Dao
interface CommentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllComments(comment: List<CommentsResponse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentsResponse: CommentsResponse)


    @Query("SELECT * FROM CommentsResponse WHERE issue_url = :issue_url")
    fun getAllComments(issue_url:String): Single<List<CommentsResponse>>


}