package com.rahul.gamechangeassignment.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["id"])
data class CommentsResponse(

    @field:SerializedName("issue_url")
    @ColumnInfo(name = "issue_url") var issue_url: String,

    @field:SerializedName("id")
    @ColumnInfo(name = "id") var id: Int,

    @field:SerializedName("user")
    @Embedded
    var user: User,

    @field:SerializedName("body")
    @ColumnInfo(name = "body") var body: String


)
