package com.kryptkode.template.app.utils.binding

import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import com.google.android.material.navigation.NavigationView

/**
 * Created by kryptkode on 2/11/2020.
 */

interface NavigationViewItemClickListener {
    fun onItemClick(@IdRes itemId: Int)
}

@BindingAdapter("setNavigationListener")
fun NavigationView.setNavigationListener(listener: NavigationViewItemClickListener) {
    setNavigationItemSelectedListener {
        listener.onItemClick(it.itemId)
        true
    }
}