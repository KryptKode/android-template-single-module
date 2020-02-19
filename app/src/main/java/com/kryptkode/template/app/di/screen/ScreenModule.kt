package com.kryptkode.template.app.di.screen

import android.content.Context
import androidx.fragment.app.FragmentActivity
import dagger.Module
import dagger.Provides

/**
 * Created by kryptkode on 2/19/2020.
 */
@Module
class ScreenModule(private val activity: FragmentActivity) {

    @Provides
    @ScreenScope
    fun provideContext(): Context {
        return activity
    }

    @Provides
    @ScreenScope
    fun provideActivity(): FragmentActivity {
        return activity
    }
}