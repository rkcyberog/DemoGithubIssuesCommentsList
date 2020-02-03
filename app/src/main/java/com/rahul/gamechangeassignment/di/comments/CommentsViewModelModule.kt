package com.rahul.gamechangeassignment.di.comments

import androidx.lifecycle.ViewModel
import com.rahul.gamechangeassignment.di.ViewModelKey
import com.rahul.gamechangeassignment.ui.comments.CommentsViewModel
import com.rahul.gamechangeassignment.ui.issues.IssuesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CommentsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    abstract fun bindIssuesViewModel(viewModel: CommentsViewModel): ViewModel
}