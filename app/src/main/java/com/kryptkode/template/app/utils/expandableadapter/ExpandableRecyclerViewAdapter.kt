package  com.kryptkode.template.app.utils.expandableadapter

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.utils.expandableadapter.listeners.ExpandCollapseListener
import com.kryptkode.template.app.utils.expandableadapter.listeners.GroupExpandCollapseListener
import com.kryptkode.template.app.utils.expandableadapter.listeners.OnGroupClickListener
import com.kryptkode.template.app.utils.expandableadapter.models.*
import com.kryptkode.template.app.utils.expandableadapter.viewholders.ChildViewHolder
import com.kryptkode.template.app.utils.expandableadapter.viewholders.GroupViewHolder
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
abstract class ExpandableRecyclerViewAdapter<T: ExpandableGroup<P, C>, P : Parent, C : Child, GVH : GroupViewHolder<T, P, C, *>, CVH : ChildViewHolder<T, P, C, *>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : BaseExpandableListAdapter<T, P, C, BaseRecyclerViewHolder<T, *>>(
    diffCallback
),
    ExpandCollapseListener,
    OnGroupClickListener {
    @JvmField
    protected var expandableList = ExpandableList<T, P, C>()
    private val expandCollapseController = ExpandCollapseController(expandableList, this)
    private var groupClickListener: OnGroupClickListener? = null
    private var expandCollapseListener: GroupExpandCollapseListener? = null

    /**
     * Implementation of Adapter.onCreateViewHolder(ViewGroup, int)
     * that determines if the list item is a group or a child and calls through
     * to the appropriate implementation of either [.onCreateGroupViewHolder]
     * or [.onCreateChildViewHolder]}.
     *
     * @param parent The [ViewGroup] into which the new [android.view.View]
     * will be added after it is bound to an adapter position.
     * @param viewType The view type of the new `android.view.View`.
     * @return Either a new [GroupViewHolder] or a new [ChildViewHolder]
     * that holds a `android.view.View` of the given view type.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<T, *> {
        return when (viewType) {
            ExpandableListPosition.GROUP -> {
                val gvh = onCreateGroupViewHolder(parent, viewType)
                gvh.listener = this
                gvh
            }
            ExpandableListPosition.CHILD -> {
                onCreateChildViewHolder(parent, viewType)
            }
            else -> throw IllegalArgumentException("viewType is not valid")
        }
    }

    fun submitList(list: List<T>?) {
        list?.let {
            expandableList.groups = it
        }
//        super.submitList(expandableList.groups)
        Timber.d(
            "LIST $list"
        )
        super.submitList(expandableList)
    }

    /**
     * Implementation of Adapter.onBindViewHolder(RecyclerView.ViewHolder, int)
     * that determines if the list item is a group or a child and calls through
     * to the appropriate implementation of either [.onBindGroupViewHolder]
     * or [.onBindChildViewHolder].
     *
     * @param holder Either the GroupViewHolder or the ChildViewHolder to bind data to
     * @param position The flat position (or index in the list of [ ][ExpandableList.visibleItemCount] in the list at which to bind
     */
    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<T, *>,
        position: Int
    ) {
        val item = getItem(position)
        Timber.d("TIMEE $item")
        val listPos = expandableList.getUnflattenedPosition(position)
        val group = expandableList.getExpandableGroup(listPos)
        when (listPos.type) {
            ExpandableListPosition.GROUP -> {
                onBindGroupViewHolder(holder as? GVH, position, group)
                if (isGroupExpanded(group)) {
                    (holder as? GVH)?.expand()
                } else {
                    (holder as? GVH)?.collapse()
                }
            }
            ExpandableListPosition.CHILD -> onBindChildViewHolder(
                holder as? CVH,
                position,
                group,
                listPos.childPos
            )
        }
    }


    /**
     * @return the number of group and child objects currently expanded
     * @see ExpandableList.visibleItemCount
     */
    override fun getItemCount(): Int {
        return expandableList.visibleItemCount
    }

    /**
     * Gets the view type of the item at the given position.
     *
     * @param position The flat position in the list to get the view type of
     * @return {@value ExpandableListPosition#CHILD} or {@value ExpandableListPosition#GROUP}
     * @throws RuntimeException if the item at the given position in the list is not found
     */
    override fun getItemViewType(position: Int): Int {
        return expandableList.getUnflattenedPosition(position).type
    }

    /**
     * Called when a group is expanded
     *
     * @param positionStart the flat position of the first child in the [ExpandableGroup]
     * @param itemCount the total number of children in the [ExpandableGroup]
     */
    override fun onGroupExpanded(positionStart: Int, itemCount: Int) {
        //update header
        val headerPosition = positionStart - 1
        notifyItemChanged(headerPosition)

        // only insert if there items to insert
        if (itemCount > 0) {
            notifyItemRangeInserted(positionStart, itemCount)
            if (expandCollapseListener != null) {
                val groupIndex = expandableList.getUnflattenedPosition(positionStart).groupPos
                expandCollapseListener!!.onGroupExpanded(groups[groupIndex])
            }
        }
    }

    /**
     * Called when a group is collapsed
     *
     * @param positionStart the flat position of the first child in the [ExpandableGroup]
     * @param itemCount the total number of children in the [ExpandableGroup]
     */
    override fun onGroupCollapsed(positionStart: Int, itemCount: Int) {
        //update header
        val headerPosition = positionStart - 1
        notifyItemChanged(headerPosition)

        // only remote if there items to remove
        if (itemCount > 0) {
            notifyItemRangeRemoved(positionStart, itemCount)
            if (expandCollapseListener != null) {
                //minus one to return the position of the header, not first child
                val groupIndex = expandableList.getUnflattenedPosition(positionStart - 1).groupPos
                expandCollapseListener!!.onGroupCollapsed(groups[groupIndex])
            }
        }
    }

    /**
     * Triggered by a click on a [GroupViewHolder]
     *
     * @param flatPos the flat position of the [GroupViewHolder] that was clicked
     * @return false if click expanded group, true if click collapsed group
     */
    override fun onGroupClick(flatPos: Int): Boolean {
        groupClickListener?.onGroupClick(flatPos)
        return expandCollapseController.toggleGroup(flatPos)
    }

    /**
     * @param flatPos The flat list position of the group
     * @return true if the group is expanded, *after* the toggle, false if the group is now collapsed
     */
    fun toggleGroup(flatPos: Int): Boolean {
        return expandCollapseController.toggleGroup(flatPos)
    }

    /**
     * @param group the [ExpandableGroup] being toggled
     * @return true if the group is expanded, *after* the toggle, false if the group is now collapsed
     */
    fun toggleGroup(group: ExpandableGroup<P, C>?): Boolean {
        return expandCollapseController.toggleGroup(group)
    }

    /**
     * @param flatPos the flattened position of an item in the list
     * @return true if `group` is expanded, false if it is collapsed
     */
    fun isGroupExpanded(flatPos: Int): Boolean {
        return expandCollapseController.isGroupExpanded(flatPos)
    }

    /**
     * @param group the [ExpandableGroup] being checked for its collapsed state
     * @return true if `group` is expanded, false if it is collapsed
     */
    fun isGroupExpanded(group: ExpandableGroup<P, C>?): Boolean {
        return expandCollapseController.isGroupExpanded(group)
    }

    /**
     * Stores the expanded state map across state loss.
     *
     *
     * Should be called from whatever [Activity] that hosts the RecyclerView that [ ] is attached to.
     *
     *
     * This will make sure to add the expanded state map as an extra to the
     * instance state bundle to be used in [.onRestoreInstanceState].
     *
     * @param savedInstanceState The `Bundle` into which to store the
     * expanded state map
     */
    open fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBooleanArray(EXPAND_STATE_MAP, expandableList.expandedGroupIndexes)
    }

    /**
     * Fetches the expandable state map from the saved instance state [Bundle]
     * and restores the expanded states of all of the list items.
     *
     *
     * Should be called from [Activity.onRestoreInstanceState]  in
     * the [Activity] that hosts the RecyclerView that this
     * [ExpandableRecyclerViewAdapter] is attached to.
     *
     *
     *
     * @param savedInstanceState The `Bundle` from which the expanded
     * state map is loaded
     */
    open fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(EXPAND_STATE_MAP)) {
            return
        }
        expandableList.expandedGroupIndexes = savedInstanceState.getBooleanArray(EXPAND_STATE_MAP)!!
        notifyDataSetChanged()
    }

    fun setOnGroupClickListener(listener: OnGroupClickListener?) {
        groupClickListener = listener
    }

    fun setOnGroupExpandCollapseListener(listener: GroupExpandCollapseListener?) {
        expandCollapseListener = listener
    }

    /**
     * The full list of [ExpandableGroup] backing this RecyclerView
     *
     * @return the list of [ExpandableGroup] that this object was instantiated with
     */
    val groups: List<ExpandableGroup<P, C>>
        get() = expandableList.groups

    /**
     * Called from [.onCreateViewHolder] when  the list item created is a group
     *
     * @param viewType an int returned by [ExpandableRecyclerViewAdapter.getItemViewType]
     * @param parent the [ViewGroup] in the list for which a [GVH]  is being created
     * @return A [GVH] corresponding to the group list item with the  `ViewGroup` parent
     */
    abstract fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): GVH

    /**
     * Called from [.onCreateViewHolder] when the list item created is a child
     *
     * @param viewType an int returned by [ExpandableRecyclerViewAdapter.getItemViewType]
     * @param parent the [ViewGroup] in the list for which a [CVH]  is being created
     * @return A [CVH] corresponding to child list item with the  `ViewGroup` parent
     */
    abstract fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): CVH

    /**
     * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item
     * bound to is a  child.
     *
     *
     * Bind data to the [CVH] here.
     *
     * @param holder The `CVH` to bind data to
     * @param flatPosition the flat position (raw index) in the list at which to bind the child
     * @param group The [ExpandableGroup] that the the child list item belongs to
     * @param childIndex the index of this child within it's [ExpandableGroup]
     */
    abstract fun onBindChildViewHolder(
        holder: CVH?, flatPosition: Int, group: T?,
        childIndex: Int
    )

    /**
     * Called from onBindViewHolder(RecyclerView.ViewHolder, int) when the list item bound to is a
     * group
     *
     *
     * Bind data to the [GVH] here.
     *
     * @param holder The `GVH` to bind data to
     * @param flatPosition the flat position (raw index) in the list at which to bind the group
     * @param group The [ExpandableGroup] to be used to bind data to this [GVH]
     */
    abstract fun onBindGroupViewHolder(
        holder: GVH?,
        flatPosition: Int,
        group: T?
    )

    companion object {
        private const val EXPAND_STATE_MAP = "expandable_recyclerview_adapter_expand_state_map"
    }
}