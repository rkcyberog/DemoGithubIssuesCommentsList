package com.rahul.gamechangeassignment.di.issues

import androidx.lifecycle.ViewModel
import com.rahul.gamechangeassignment.di.ViewModelKey
import com.rahul.gamechangeassignment.ui.issues.IssuesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class IssuesViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(IssuesViewModel::class)
    abstract fun bindIssuesViewModel(viewModel: IssuesViewModel): ViewModel
}