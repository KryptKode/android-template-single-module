package com.kryptkode.template.app.utils.expandable.model

/**
 * Simple implementation of the ParentListItem interface,
 * by default all items are not initially expanded.
 *
 * @param <C> Type of the Child Items held by the Parent.
</C> */
class SimpleParent<C> protected constructor(override var childList: List<C>) :
    Parent<C> {
    override val isInitiallyExpanded: Boolean
        get() = false

}