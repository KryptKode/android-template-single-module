package com.kryptkode.template.startnav.model

import androidx.recyclerview.widget.DiffUtil
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.subcategories.model.SubCategoryForView
import kotlinx.android.parcel.Parcelize

/**
 * Created by kryptkode on 3/8/2020.
 */
@Parcelize
data class CategoryWithSubCategoriesForView(
    val category: CategoryForView,
    val subcategories: List<SubCategoryForView>
) : ExpandableGroup<CategoryForView, SubCategoryForView>(category, subcategories) {
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CategoryWithSubCategoriesForView>() {
            override fun areItemsTheSame(
                oldItem: CategoryWithSubCategoriesForView,
                newItem: CategoryWithSubCategoriesForView
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CategoryWithSubCategoriesForView,
                newItem: CategoryWithSubCategoriesForView
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}