package  com.kryptkode.template.app.utils.expandableadapter.listeners

import  com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup

interface GroupExpandCollapseListener {
    /**
     * Called when a group is expanded
     * @param group the [ExpandableGroup] being expanded
     */
    fun onGroupExpanded(group: ExpandableGroup<*, *>?)

    /**
     * Called when a group is collapsed
     * @param group the [ExpandableGroup] being collapsed
     */
    fun onGroupCollapsed(group: ExpandableGroup<*, *>?)
}