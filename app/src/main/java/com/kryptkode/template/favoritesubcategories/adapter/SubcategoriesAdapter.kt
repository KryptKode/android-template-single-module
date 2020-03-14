package com.kryptkode.template.favoritesubcategories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.template.app.base.recycler.BaseRecyclerAdapter
import com.kryptkode.template.databinding.ItemCategoryGridBinding
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/2/2020.
 */
class SubcategoriesAdapter (private val subcategoriesListener: SubcategoriesListener) : BaseRecyclerAdapter<SubCategoryForView, SubcategoriesViewHolder>(SubCategoryForView.diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoriesViewHolder {
        return SubcategoriesViewHolder(subcategoriesListener, ItemCategoryGridBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
}