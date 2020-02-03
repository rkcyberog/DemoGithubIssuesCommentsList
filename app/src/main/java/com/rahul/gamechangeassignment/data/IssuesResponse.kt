package com.rahul.gamechangeassignment.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["number"])
data class IssuesResponse(

                         @field:SerializedName("url")
                         @ColumnInfo(name = "url") var url: String,


                        @field:SerializedName("number")
                      @ColumnInfo(name = "number") var number: Int,


                        @field:SerializedName("title")
                      @ColumnInfo(name = "title") var title: String,

                        @field:SerializedName("body")
                      @ColumnInfo(name = "body") var body: String


)