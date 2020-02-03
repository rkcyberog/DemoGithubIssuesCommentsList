package com.rahul.gamechangeassignment.ui.comments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.gamechangeassignment.R
import com.rahul.gamechangeassignment.data.CommentsResponse
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_issues.*
import java.util.ArrayList
import javax.inject.Inject

class ListOfCommentsActivity : DaggerAppCompatActivity() {

    val issue_is:String?=null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var commentsViewModel: CommentsViewModel
    var commentsListAdapter = CommentsListAdapter(ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_comments)
        initializeRecycler()
        commentsViewModel = ViewModelProviders.of(this, viewModelFactory)[CommentsViewModel::class.java]
        val bundle :Bundle ?=intent.extras
        val issue_id = bundle!!.getString("issue_id")
        val issue_url = bundle!!.getString("issue_url")
        Toast.makeText(this, issue_id, Toast.LENGTH_SHORT).show()
        progressBar.visibility = View.VISIBLE
        if (issue_id != null&&issue_url!=null) {
            commentsViewModel.loadComments(issue_id,issue_url)
        }
        commentsViewModel.commentsResult().observe(this,
            Observer<List<CommentsResponse>> {
                if (it != null) {
                    if(it.isEmpty()){
                        val dialogBuilder = AlertDialog.Builder(this)
                        dialogBuilder.setMessage("Sorry no comments found related to this issue")

                            .setCancelable(false)

                            .setPositiveButton("Got it", DialogInterface.OnClickListener {
                                    dialog, id -> finish()
                            })

                        val alert = dialogBuilder.create()
                        alert.show()

                    }
                    Log.i("item",it.toString())
                    commentsListAdapter.addComments(it)
                    recycler.adapter = commentsListAdapter

                }
            })

        commentsViewModel.commentsError().observe(this, Observer<String> {
            if (it != null) {
                Log.i("Error",it.toString())
                Toast.makeText(this, resources.getString(R.string.issues_error) + it,
                    Toast.LENGTH_SHORT).show()
            }
        })

        commentsViewModel.commentsLoader().observe(this, Observer<Boolean> {
            if (it == false) progressBar.visibility = View.GONE
        })
    }

    private fun initializeRecycler() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager

        }
    }

    override fun onDestroy() {
        commentsViewModel.disposeElements()
        super.onDestroy()
    }
}