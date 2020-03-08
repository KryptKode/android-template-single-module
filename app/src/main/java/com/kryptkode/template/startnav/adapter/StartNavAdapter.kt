package com.kryptkode.template.startnav.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptkode.template.app.utils.expandableadapter.ExpandableRecyclerViewAdapter
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemExpandableCategoryBinding
import com.kryptkode.template.databinding.ItemExpandableSubcategoryBinding
import com.kryptkode.template.startnav.adapter.viewholder.CategoryGroupViewHolder
import com.kryptkode.template.startnav.adapter.viewholder.SubCategoryChildViewHolder
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/8/2020.
 */
class StartNavAdapter :
    ExpandableRecyclerViewAdapter<CategoryWithSubCategoriesForView, CategoryForView, SubCategoryForView, CategoryGroupViewHolder, SubCategoryChildViewHolder>(
        CategoryWithSubCategoriesForView.diffUtil
    ) {

    override fun onCreateGroupViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): CategoryGroupViewHolder {
        return CategoryGroupViewHolder(
            ItemExpandableCategoryBinding.inflate(
                LayoutInflater.from(
                    parent?.context
                ), parent, false
            )
        )
    }


    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): SubCategoryChildViewHolder {
        return SubCategoryChildViewHolder(
            ItemExpandableSubcategoryBinding.inflate(
                LayoutInflater.from(
                    parent?.context
                ), parent, false
            )
        )
    }

    override fun onBindChildViewHolder(
        holder: SubCategoryChildViewHolder?,
        flatPosition: Int,
        group: CategoryWithSubCategoriesForView?,
        childIndex: Int
    ) {
        holder?.performBind(group, childIndex)
    }

    override fun onBindGroupViewHolder(
        holder: CategoryGroupViewHolder?,
        flatPosition: Int,
        group: CategoryWithSubCategoriesForView?
    ) {
        holder?.performBind(group)
    }

}