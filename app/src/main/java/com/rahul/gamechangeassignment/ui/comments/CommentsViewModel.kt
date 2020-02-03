package com.rahul.gamechangeassignment.ui.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahul.gamechangeassignment.data.CommentsResponse
import com.rahul.gamechangeassignment.data.repository.IssueRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {

    var commentsResult: MutableLiveData<List<CommentsResponse>> = MutableLiveData()
    var commentsError: MutableLiveData<String> = MutableLiveData()
    var commentsLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<CommentsResponse>>

    fun commentsResult(): LiveData<List<CommentsResponse>> {
        return commentsResult
    }

    fun commentsError(): LiveData<String> {
        return commentsError
    }

    fun commentsLoader(): LiveData<Boolean> {
        return commentsLoader
    }

    fun loadComments(issue_id:String,issue_url:String) {

        disposableObserver = object : DisposableObserver<List<CommentsResponse>>() {
            override fun onComplete() {

            }
            override fun onNext(issues: List<CommentsResponse>) {
                commentsResult.postValue(issues)
                commentsLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                commentsError.postValue(e.message)
                commentsLoader.postValue(false)
            }
        }

        issueRepository.getCommentsList(issue_id,issue_url )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }


    fun disposeElements(){
        if(null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}