package com.rahul.gamechangeassignment.data.repository

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.rahul.gamechangeassignment.data.CommentsResponse
import com.rahul.gamechangeassignment.data.local.dao.IssuesDao
import com.rahul.gamechangeassignment.data.IssuesResponse
import com.rahul.gamechangeassignment.data.local.dao.CommentsDao
import com.rahul.gamechangeassignment.data.remote.comments.CommentsApi
import com.rahul.gamechangeassignment.data.remote.issues.IssuesApi
import com.rahul.gamechangeassignment.utils.Utils
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepository @Inject constructor(val issuesApi: IssuesApi,val commentsApi: CommentsApi,
                                          val issuesDao: IssuesDao, val commentsDao: CommentsDao,val app:Application, val utils: Utils
) {
    var shouldFetch: Boolean=false

    fun getIssuesList(): Observable<List<IssuesResponse>> {
        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<IssuesResponse>>? = null
        val observableFromDb = getIssuesFromDb()

        if (hasConnection && shouldFetch&&refreshData()){
            observableFromApi = getIssuesFromApi()
        }
        return if (hasConnection && shouldFetch&&refreshData()) Observable.concatArrayEager(observableFromApi, observableFromDb)
        else observableFromDb
    }

    fun getIssuesFromApi(): Observable<List<IssuesResponse>> {
        return issuesApi.listOfIssues()
            .doOnNext {
                Log.e("REPOSITORY API * ", it.toString())
                Log.e("REPOSITORY API * ", it.size.toString())
                for (item in it) {
                    Log.i("item",item.toString())
                    issuesDao.insertIssues(item)
                }
            }
    }

    fun getIssuesFromDb(): Observable<List<IssuesResponse>> {
        return issuesDao.getAllIssues()
            .toObservable()
            .doOnNext {
                if(it.isEmpty())
                    shouldFetch=true
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }

    fun getCommentsList(issue_id: String,issue_url:String): Observable<List<CommentsResponse>> {
        val hasConnection = utils.isConnectedToInternet()
        var observableFromApi: Observable<List<CommentsResponse>>? = null
        val observableFromDb = getCommentsFromDb(issue_id.toInt(),issue_url)
        if (hasConnection && shouldFetch&&refreshData()){
            observableFromApi = getCommentsFromApi(issue_id)
        }
        return if (hasConnection && shouldFetch&&refreshData()) Observable.concatArrayEager(observableFromApi, observableFromDb)
        else observableFromDb
    }

    fun getCommentsFromApi(issue_id: String): Observable<List<CommentsResponse>> {
        return commentsApi.listOfComments(issue_id)
            .doOnNext {
                Log.e("REPOSITORY API * ", it.toString())
                Log.e("REPOSITORY API * ", it.size.toString())
                for (item in it) {
                    Log.i("item",item.toString())
                    commentsDao.insertComment(item)
                }
            }
    }

    fun getCommentsFromDb(issue_id: Int,issue_url: String): Observable<List<CommentsResponse>> {
        return commentsDao.getAllComments(issue_url)
            .toObservable()
            .doOnNext {
                if(it.isEmpty())
                    shouldFetch=true
                Log.e("REPOSITORY DB *** ", it.size.toString())
            }
    }

    fun refreshData():Boolean{
        val c = Calendar.getInstance()
        val thisDay =
            c[Calendar.DAY_OF_MONTH]

        val todayMillis = c.timeInMillis
        val prefs: SharedPreferences = app.getSharedPreferences("MYPREF", Context.MODE_PRIVATE)
        val last = prefs.getLong("date", 0)

        c.timeInMillis = last
        val lastDay = c[Calendar.DAY_OF_MONTH]

        return if (last == 0L || lastDay != thisDay) {
            val edit = prefs.edit()
            edit.putLong("date", todayMillis)
            edit.commit()
            false
        }else true
    }

}

