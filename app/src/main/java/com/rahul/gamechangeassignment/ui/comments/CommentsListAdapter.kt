package com.rahul.gamechangeassignment.ui.comments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahul.gamechangeassignment.R
import com.rahul.gamechangeassignment.data.CommentsResponse
import java.util.ArrayList


class CommentsListAdapter(issues: List<CommentsResponse>?) : RecyclerView.Adapter<CommentsListAdapter.CommentsVieHolder>() {

  private var commentsList = ArrayList<CommentsResponse>()

  init {
    this.commentsList = issues as ArrayList<CommentsResponse>
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsVieHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(
      R.layout.comments_list_item,
      parent, false)
    return CommentsVieHolder(itemView)  }


  override fun onBindViewHolder(holder: CommentsVieHolder, position: Int) {
    val commentItem = commentsList[position]

    holder?.issuesListItem(commentItem)
  }

  override fun getItemCount(): Int {
    return commentsList.size
  }



  fun addComments(issues: List<CommentsResponse>){
    val initPosition = commentsList.size
    commentsList.addAll(issues)
    notifyItemRangeInserted(initPosition, commentsList.size)
  }

  inner class CommentsVieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var commentUser = itemView.findViewById<TextView>(R.id.comment_user)
    var commentBody = itemView.findViewById<TextView>(R.id.comment_body)


    fun issuesListItem(commentItem: CommentsResponse) {
      commentUser.text = commentItem.user.login
      commentBody.text = commentItem.body

    }
  }


}
