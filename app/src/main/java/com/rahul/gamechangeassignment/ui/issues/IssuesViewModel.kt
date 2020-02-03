package com.rahul.gamechangeassignment.ui.issues

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahul.gamechangeassignment.data.IssuesResponse
import com.rahul.gamechangeassignment.data.repository.IssueRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class IssuesViewModel  @Inject constructor(
    private val issueRepository: IssueRepository) : ViewModel() {

    var issuesResult: MutableLiveData<List<IssuesResponse>> = MutableLiveData()
    var issuesError: MutableLiveData<String> = MutableLiveData()
    var issuesLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<List<IssuesResponse>>

    fun issuesResult(): LiveData<List<IssuesResponse>> {
        return issuesResult
    }

    fun issuesError(): LiveData<String> {
        return issuesError
    }

    fun issuesLoader(): LiveData<Boolean> {
        return issuesLoader
    }

    fun loadIssues() {

        disposableObserver = object : DisposableObserver<List<IssuesResponse>>() {
            override fun onComplete() {

            }
            override fun onNext(issues: List<IssuesResponse>) {
                issuesResult.postValue(issues)
                issuesLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                issuesError.postValue(e.message)
                issuesLoader.postValue(false)
            }
        }

        issueRepository.getIssuesList()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(disposableObserver)
    }


    fun disposeElements(){
        if(null != disposableObserver && !disposableObserver.isDisposed) disposableObserver.dispose()
    }

}