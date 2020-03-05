package com.kryptkode.template.app.base.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.kryptkode.template.BR
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseViewModelActivity<D, V>(klazz: KClass<V>) :
    BaseActivity() where D : ViewDataBinding, V : BaseViewModel {

    protected lateinit var binding: D

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel by viewModels(klazz) { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutResourceId())
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }


    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

}

@MainThread
fun <VM : ViewModel> ComponentActivity.viewModels(
    klazz: KClass<VM>,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }

    return ViewModelLazy(klazz, { viewModelStore }, factoryPromise)
}