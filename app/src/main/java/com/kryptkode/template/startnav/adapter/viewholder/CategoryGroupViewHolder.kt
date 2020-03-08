package com.kryptkode.template.startnav.adapter.viewholder

import com.kryptkode.template.app.utils.expandableadapter.viewholders.GroupViewHolder
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemExpandableCategoryBinding
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/8/2020.
 */
class CategoryGroupViewHolder(binding: ItemExpandableCategoryBinding) :
    GroupViewHolder<CategoryWithSubCategoriesForView, CategoryForView, SubCategoryForView, ItemExpandableCategoryBinding>(binding) {

    override fun performBindGroup(item: CategoryForView?) {

    }
}