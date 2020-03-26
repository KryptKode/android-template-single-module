package com.kryptkode.template.app.utils.extensions

import com.kryptkode.template.cardlist.adapter.CardListener
import com.kryptkode.template.cardlist.adapter.items.CardItem
import com.kryptkode.template.cardlist.model.CardForView
import com.kryptkode.template.startnav.adapter.ChildItem
import com.kryptkode.template.startnav.adapter.HeaderItem
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

/**
 * Created by kryptkode on 3/10/2020.
 */
fun GroupAdapter<GroupieViewHolder>.populateCategoriesAndSubcategories(items: List<CategoryWithSubCategoriesForView>?) {
    val allGroups = mutableListOf<Group>()
    items?.forEach {
        val headerItem = HeaderItem(it.category)
        val expandableGroup = ExpandableGroup(headerItem)
        val childItems = it.subcategories.map { item ->
            ChildItem(it.category, item)
        }
        expandableGroup.addAll(childItems)
        allGroups.add(expandableGroup)
        update(allGroups)
    }
}

fun GroupAdapter<GroupieViewHolder>.populateCards(
    items: List<CardForView>,
    cardListener: CardListener
) {
    val allGroups = mutableListOf<Group>()
    items.forEach {
        allGroups.add(CardItem(it, cardListener))
    }
    updateAsync(allGroups)
}

fun GroupAdapter<GroupieViewHolder>.addNativeAdToCardList(){

}

