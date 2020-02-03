package com.rahul.gamechangeassignment.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("login")
    @Expose
    var login: String
)