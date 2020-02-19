package com.kryptkode.template.app.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseBindingFragment<D>() :
    BaseFragment() where D : ViewDataBinding {


    protected lateinit var binding: D

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
        return binding.root
    }


    @LayoutRes
    abstract fun getLayoutResource(): Int

}