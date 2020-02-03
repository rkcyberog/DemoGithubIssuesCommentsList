package com.rahul.gamechangeassignment.data.remote.comments

import com.rahul.gamechangeassignment.data.CommentsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentsApi {

    @GET("repos/firebase/firebase-ios-sdk/issues/{issue_id}/comments")
    fun listOfComments(
        @Path("issue_id") issue_id: String
    ): Observable<List<CommentsResponse>>
}
