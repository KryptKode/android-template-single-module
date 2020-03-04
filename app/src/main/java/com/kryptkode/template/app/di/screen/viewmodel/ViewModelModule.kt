package com.kryptkode.template.app.di.screen.viewmodel

import androidx.lifecycle.ViewModel
import com.kryptkode.template.categories.CategoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

}