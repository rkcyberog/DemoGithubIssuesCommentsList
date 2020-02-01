package com.rahul.gamechangeassignment.di

import com.rahul.gamechangeassignment.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
 abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): MainActivity



}