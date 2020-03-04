package com.kryptkode.template.categories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.template.app.base.recycler.BaseRecyclerAdapter
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemCategoryGridBinding

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoriesAdapter (private val categoriesListener: CategoriesListener) : BaseRecyclerAdapter<CategoryForView, CategoriesViewHolder>(CategoryForView.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(categoriesListener, ItemCategoryGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
}