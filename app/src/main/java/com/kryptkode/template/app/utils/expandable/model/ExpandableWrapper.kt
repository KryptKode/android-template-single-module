package com.kryptkode.template.app.utils.expandable.model

import java.util.*

/**
 * Wrapper used to link metadata with a list item.
 *
 * @param <P> Parent list item
 * @param <C> Child list item
</C></P> */
class ExpandableWrapper<P : Parent<C>?, C> {
    var parent: P? = null
        private set
    var child: C? = null
        private set
    private var mWrappedParent: Boolean
    var isExpanded: Boolean
    private var mWrappedChildList: List<ExpandableWrapper<P, C>>? = null

    /**
     * Constructor to wrap a parent object of type [P].
     *
     * @param parent The parent object to wrap
     */
    constructor(parent: P) {
        this.parent = parent
        mWrappedParent = true
        isExpanded = false
        mWrappedChildList = generateChildItemList(parent)
    }

    /**
     * Constructor to wrap a child object of type [C]
     *
     * @param child The child object to wrap
     */
    constructor(child: C) {
        this.child = child
        mWrappedParent = false
        isExpanded = false
    }

    fun setParent(parent: P) {
        this.parent = parent
        mWrappedChildList = generateChildItemList(parent)
    }

    fun isParent(): Boolean {
        return mWrappedParent
    }

    /**
     * @return The initial expanded state of a parent
     * @throws IllegalStateException If a parent isn't being wrapped
     */
    val isParentInitiallyExpanded: Boolean
        get() {
            check(mWrappedParent) { "Parent not wrapped" }
            return parent!!.isInitiallyExpanded
        }

    /**
     * @return The list of children of a parent
     * @throws IllegalStateException If a parent isn't being wrapped
     */
    val wrappedChildList: List<ExpandableWrapper<P, C>>?
        get() {
            check(mWrappedParent) { "Parent not wrapped" }
            return mWrappedChildList
        }

    private fun generateChildItemList(parentListItem: P): List<ExpandableWrapper<P, C>> {
        val childItemList: MutableList<ExpandableWrapper<P, C>> =
            ArrayList()
        for (child in parentListItem!!.childList) {
            childItemList.add(ExpandableWrapper(child))
        }
        return childItemList
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ExpandableWrapper<*, *>
        if (if (parent != null) parent != that.parent else that.parent != null) return false
        return if (child != null) child == that.child else that.child == null
    }

    override fun hashCode(): Int {
        var result = if (parent != null) parent.hashCode() else 0
        result = 31 * result + if (child != null) child.hashCode() else 0
        return result
    }
}