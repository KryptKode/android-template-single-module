package com.kryptkode.template.startnav.adapter.viewholder

import com.kryptkode.template.app.utils.expandableadapter.models.Child
import com.kryptkode.template.app.utils.expandableadapter.viewholders.ChildViewHolder
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemExpandableSubcategoryBinding
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/8/2020.
 */
class SubCategoryChildViewHolder(binding: ItemExpandableSubcategoryBinding) :
    ChildViewHolder<CategoryWithSubCategoriesForView, CategoryForView, SubCategoryForView, ItemExpandableSubcategoryBinding>(binding) {

    override fun performBind(child: Child?) {

    }
}