package com.kryptkode.template.startnav.adapter

import com.kryptkode.template.R
import com.kryptkode.template.databinding.ItemExpandableSubcategoryBinding
import com.kryptkode.template.subcategories.model.SubCategoryForView
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by kryptkode on 3/10/2020.
 */
class ChildItem(val subCategoryForView: SubCategoryForView) :
    BindableItem<ItemExpandableSubcategoryBinding>() {
    override fun bind(viewBinding: ItemExpandableSubcategoryBinding, position: Int) {
        viewBinding.nameTextView.text = subCategoryForView.name
    }

    override fun getLayout(): Int {
        return R.layout.item_expandable_subcategory
    }
}