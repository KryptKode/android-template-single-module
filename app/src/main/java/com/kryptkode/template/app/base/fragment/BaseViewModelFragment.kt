package com.kryptkode.template.app.base.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kryptkode.template.BR
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by kryptkode on 2/18/2020.
 */
abstract class BaseViewModelFragment<D, V>(klazz: KClass<V>) :
    BaseBindingFragment<D>() where D : ViewDataBinding, V : ViewModel {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by viewModels(klazz) { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setVariable(BR._all, viewModel)
    }

}


/**
 * Helper method for creation of [ViewModel], that takes the class as a parameter because using
 * [androidx.fragment.app.viewModels] cannot resolve the reified generic
 */
@Suppress("NOTHING_TO_INLINE")
@MainThread
inline fun <VM : ViewModel> Fragment.viewModels(
    viewModelClass: KClass<VM>,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(viewModelClass, { this.viewModelStore }, factoryProducer)