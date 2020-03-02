package com.kryptkode.template.app.di.screen

import com.kryptkode.template.MainActivity
import com.kryptkode.template.app.di.viewmodel.ViewModelFactoryModule
import com.kryptkode.template.app.di.viewmodel.ViewModelModule
import com.kryptkode.template.ui.home.HomeFragment
import dagger.Subcomponent

/**
 * Created by kryptkode on 2/19/2020.
 */

@Subcomponent(
    modules = [
        ScreenModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
interface ScreenComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: HomeFragment)
}