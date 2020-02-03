package com.rahul.gamechangeassignment.ui.issues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahul.gamechangeassignment.R
import com.rahul.gamechangeassignment.data.IssuesResponse
import java.util.ArrayList


class IssuesListAdapter( issues: List<IssuesResponse>?) : RecyclerView.Adapter<IssuesListAdapter.IssuesVieHolder>() {

  private var issuesList = ArrayList<IssuesResponse>()
  var onItemClick: ((IssuesResponse) -> Unit)? = null
  init {
    this.issuesList = issues as ArrayList<IssuesResponse>
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesVieHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(
      R.layout.issues_list_item,
      parent, false)
    return IssuesVieHolder(itemView)  }


  override fun onBindViewHolder(holder: IssuesVieHolder, position: Int) {
    val issuesItem = issuesList[position]

    holder?.issuesListItem(issuesItem)
  }

  override fun getItemCount(): Int {
    return issuesList.size
  }



  fun addIssues(issues: List<IssuesResponse>){
    val initPosition = issuesList.size
    issuesList.addAll(issues)
    notifyItemRangeInserted(initPosition, issuesList.size)
  }

  inner class IssuesVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
      itemView.setOnClickListener {
        onItemClick?.invoke(issuesList[adapterPosition])
      }
    }

    var issueNumber = itemView.findViewById<TextView>(R.id.issues_number)
    var issueTitle = itemView.findViewById<TextView>(R.id.issues_title)
    var issueBody = itemView.findViewById<TextView>(R.id.issues_body)

    fun issuesListItem(issuesItem: IssuesResponse) {
      issueNumber.text = issuesItem.number.toString()
      issueTitle.text = issuesItem.title
      issueBody.text = if (issuesItem.body.length<140) {
        issuesItem.body
      } else {
        issuesItem.body.substring(0,140)
      }
    }
  }


}
