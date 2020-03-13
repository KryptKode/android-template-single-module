package com.kryptkode.template.app.di.screen

import com.kryptkode.template.MainActivity
import com.kryptkode.template.app.di.screen.modules.ScreenModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelFactoryModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelModule
import com.kryptkode.template.app.di.screen.viewmodel.ViewModelProviderModule
import com.kryptkode.template.cardlist.CardListFragment
import com.kryptkode.template.categories.CategoriesFragment
import com.kryptkode.template.startnav.StartNavFragment
import com.kryptkode.template.subcategories.SubcategoriesFragment
import dagger.Subcomponent

/**
 * Created by kryptkode on 2/19/2020.
 */

@Subcomponent(
    modules = [
        ScreenModule::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class,
        ViewModelProviderModule::class
    ]
)
@ScreenScope
interface ScreenComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: CategoriesFragment)
    fun inject(startNavFragment: StartNavFragment)
    fun inject(subcategoriesFragment: SubcategoriesFragment)
    fun inject(cardListFragment: CardListFragment)
}