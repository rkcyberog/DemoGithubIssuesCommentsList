package com.rahul.gamechangeassignment.ui.issues

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rahul.gamechangeassignment.R
import com.rahul.gamechangeassignment.data.IssuesResponse
import com.rahul.gamechangeassignment.ui.comments.ListOfCommentsActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_list_issues.*
import java.util.*
import javax.inject.Inject


class ListOfIssuesActivity: DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var issuesViewModel: IssuesViewModel
    var issuesListAdapter = IssuesListAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_issues)
        initializeRecycler()
         issuesViewModel = ViewModelProviders.of(this, viewModelFactory)[IssuesViewModel::class.java]
        issuesListAdapter.onItemClick = { issue ->
            val value1: String = issue.number.toString()
            val value2: String = issue.url
            val intent = Intent(this, ListOfCommentsActivity::class.java)
            intent.putExtra("issue_id", value1)
            intent.putExtra("issue_url", value2)
            startActivity(intent)
            Log.d("TAG", issue.number.toString())
        }


        progressBar.visibility = View.VISIBLE
        issuesViewModel.loadIssues()
        issuesViewModel.issuesResult().observe(this,
            Observer<List<IssuesResponse>> {
                if (it != null) {
                    Log.i("item",it.toString())
                    issuesListAdapter.addIssues(it)
                    recycler.adapter = issuesListAdapter

                }
            })

        issuesViewModel.issuesError().observe(this, Observer<String> {
            if (it != null) {
                Log.i("Error",it.toString())
                Toast.makeText(this, resources.getString(R.string.issues_error) + it,
                    Toast.LENGTH_SHORT).show()
            }
        })

        issuesViewModel.issuesLoader().observe(this, Observer<Boolean> {
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
        issuesViewModel.disposeElements()
        super.onDestroy()
    }
}