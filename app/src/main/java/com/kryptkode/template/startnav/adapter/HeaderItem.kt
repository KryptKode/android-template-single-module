package com.kryptkode.template.startnav.adapter

import android.graphics.drawable.Animatable
import com.kryptkode.template.R
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.databinding.ItemExpandableCategoryBinding
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.databinding.BindableItem

/**
 * Created by kryptkode on 3/10/2020.
 */
class HeaderItem(private val categoryForView: CategoryForView) :
    BindableItem<ItemExpandableCategoryBinding>(), ExpandableItem {
    private lateinit var expandableGroup: ExpandableGroup

    override fun bind(viewBinding: ItemExpandableCategoryBinding, position: Int) {
        viewBinding.nameTextView.text = categoryForView.name.capitalize()
        viewBinding.toggleExpandImageView.setImageResource(if (expandableGroup.isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand)
        viewBinding.root.setOnClickListener {
            expandableGroup.onToggleExpanded()
            changeToggleIcon(viewBinding)
        }
    }

    private fun changeToggleIcon(viewBinding: ItemExpandableCategoryBinding) {
        viewBinding.toggleExpandImageView.apply {
            setImageResource(if (expandableGroup.isExpanded) R.drawable.ani_collapse_animated else R.drawable.ani_expand_animated)
            (drawable as Animatable).start()
        }
    }

    override fun getLayout(): Int {
        return R.layout.item_expandable_category
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}