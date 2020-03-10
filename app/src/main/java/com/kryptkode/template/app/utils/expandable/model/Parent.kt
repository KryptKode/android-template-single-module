package com.kryptkode.template.app.utils.expandable.model

/**
 * Interface for implementing required methods in a parent.
 */
interface Parent<C> {
    /**
     * Getter for the list of this parent's child items.
     *
     *
     * If list is empty, the parent has no children.
     *
     * @return A [List] of the children of this [Parent]
     */
    val childList: List<C>?

    /**
     * Getter used to determine if this [Parent]'s
     * [android.view.View] should show up initially as expanded.
     *
     * @return true if expanded, false if not
     */
    val isInitiallyExpanded: Boolean
}