package com.kryptkode.template.app.utils.extensions

import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.adapter.items.CardItem
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.startnav.adapter.ChildItem
import com.kryptkode.template.startnav.adapter.HeaderItem
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

/**
 * Created by kryptkode on 3/10/2020.
 */
fun GroupAdapter<GroupieViewHolder>.populateCategoriesAndSubcategories(items: List<CategoryWithSubCategoriesForView>?) {
    updateAsync(items?.map {
        val headerItem = HeaderItem(it.category)
        val expandableGroup = ExpandableGroup(headerItem)
        val childItems = it.subcategories.map { item ->
            ChildItem(it.category, item)
        }
        expandableGroup.addAll(childItems)
        expandableGroup
    } ?: listOf())
}


fun GroupAdapter<GroupieViewHolder>.populateCards(
    items: List<CardForView>,
    cardListener: CardListener
) {

    updateAsync(items.map {
        CardItem(it, cardListener)
    })

}

