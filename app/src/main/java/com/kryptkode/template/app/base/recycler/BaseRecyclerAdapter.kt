package com.kryptkode.template.app.base.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseRecyclerAdapter<T, V>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, V>(diffCallback) where V : BaseRecyclerViewHolder<T, *> {

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.performBind(getItem(position))
    }
}