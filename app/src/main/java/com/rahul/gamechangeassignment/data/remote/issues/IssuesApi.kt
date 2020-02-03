package com.rahul.gamechangeassignment.data.remote.issues

import com.rahul.gamechangeassignment.data.IssuesResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface IssuesApi {

    @GET("repos/firebase/firebase-ios-sdk/issues")
    fun listOfIssues(): Observable<List<IssuesResponse>>
}