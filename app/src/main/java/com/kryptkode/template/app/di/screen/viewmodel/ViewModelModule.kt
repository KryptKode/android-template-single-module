package com.kryptkode.template.app.di.screen.viewmodel

import androidx.lifecycle.ViewModel
import com.kryptkode.template.MainActivityViewModel
import com.kryptkode.template.cardlist.CardListViewModel
import com.kryptkode.template.categories.CategoriesViewModel
import com.kryptkode.template.startnav.StartNavViewModel
import com.kryptkode.template.subcategories.SubcategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StartNavViewModel::class)
    abstract fun bindStartNavViewModel(viewModel: StartNavViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubcategoriesViewModel::class)
    abstract fun bindSubcategoriesViewModel(viewModel: SubcategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CardListViewModel::class)
    abstract fun bindCardListViewModel(viewModel: CardListViewModel): ViewModel


}