package com.rahul.gamechangeassignment.di

import com.rahul.gamechangeassignment.di.comments.CommentsViewModelModule
import com.rahul.gamechangeassignment.di.issues.IssuesViewModelModule
import com.rahul.gamechangeassignment.ui.comments.CommentsViewModel
import com.rahul.gamechangeassignment.ui.comments.ListOfCommentsActivity
import com.rahul.gamechangeassignment.ui.issues.IssuesViewModel
import com.rahul.gamechangeassignment.ui.issues.ListOfIssuesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
 abstract class ActivityBuildersModule {

    @ContributesAndroidInjector( modules = [IssuesViewModelModule::class])
    abstract fun contributeListOfIssuesActivity(): ListOfIssuesActivity

    @ContributesAndroidInjector( modules = [CommentsViewModelModule::class])
    abstract fun contributeListOfCommentsActivity(): ListOfCommentsActivity

}