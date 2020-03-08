package  com.kryptkode.template.app.utils.expandableadapter.models

import android.widget.ExpandableListView
import java.util.*

/**
 * Exact copy of android.widget.ExpandableListPosition because
 * android.widget.ExpandableListPosition has package local scope
 *
 *
 * ExpandableListPosition can refer to either a group's position or a child's
 * position. Referring to a child's position requires both a group position (the
 * group containing the child) and a child position (the child's position within
 * that group). To create objects, use [.obtainChildPosition] or
 * [.obtainGroupPosition].
 */
class ExpandableListPosition private constructor() {
    /**
     * The position of either the group being referred to, or the parent
     * group of the child being referred to
     */
    @JvmField
    var groupPos = 0

    /**
     * The position of the child within its parent group
     */
    @JvmField
    var childPos = 0

    /**
     * The position of the item in the flat list (optional, used internally when
     * the corresponding flat list position for the group or child is known)
     */
    var flatListPos = 0

    /**
     * What type of position this ExpandableListPosition represents
     */
    @JvmField
    var type = 0
    private fun resetState() {
        groupPos = 0
        childPos = 0
        flatListPos = 0
        type = 0
    }

    val packedPosition: Long
        get() = if (type == CHILD) {
            ExpandableListView.getPackedPositionForChild(groupPos, childPos)
        } else {
            ExpandableListView.getPackedPositionForGroup(groupPos)
        }

    /**
     * Do not call this unless you obtained this via ExpandableListPosition.obtain().
     * PositionMetadata will handle recycling its own children.
     */
    fun recycle() {
        synchronized(sPool) {
            if (sPool.size < MAX_POOL_SIZE) {
                sPool.add(this)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as ExpandableListPosition
        if (groupPos != that.groupPos) return false
        if (childPos != that.childPos) return false
        return if (flatListPos != that.flatListPos) false else type == that.type
    }

    override fun hashCode(): Int {
        var result = groupPos
        result = 31 * result + childPos
        result = 31 * result + flatListPos
        result = 31 * result + type
        return result
    }

    override fun toString(): String {
        return "ExpandableListPosition{" +
                "groupPos=" + groupPos +
                ", childPos=" + childPos +
                ", flatListPos=" + flatListPos +
                ", type=" + type +
                '}'
    }

    companion object {
        private const val MAX_POOL_SIZE = 5
        private val sPool = ArrayList<ExpandableListPosition>(MAX_POOL_SIZE)

        /**
         * This data type represents a child position
         */
        const val CHILD = 1

        /**
         * This data type represents a group position
         */
        const val GROUP = 2
        fun obtainGroupPosition(groupPosition: Int): ExpandableListPosition {
            return obtain(GROUP, groupPosition, 0, 0)
        }

        fun obtainChildPosition(groupPosition: Int, childPosition: Int): ExpandableListPosition {
            return obtain(CHILD, groupPosition, childPosition, 0)
        }

        fun obtainPosition(packedPosition: Long): ExpandableListPosition? {
            if (packedPosition == ExpandableListView.PACKED_POSITION_VALUE_NULL) {
                return null
            }
            val elp = recycledOrCreate
            elp.groupPos = ExpandableListView.getPackedPositionGroup(packedPosition)
            if (ExpandableListView.getPackedPositionType(packedPosition) ==
                    ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                elp.type = CHILD
                elp.childPos = ExpandableListView.getPackedPositionChild(packedPosition)
            } else {
                elp.type = GROUP
            }
            return elp
        }

        @JvmStatic
        fun obtain(type: Int, groupPos: Int, childPos: Int,
                   flatListPos: Int): ExpandableListPosition {
            val elp = recycledOrCreate
            elp.type = type
            elp.groupPos = groupPos
            elp.childPos = childPos
            elp.flatListPos = flatListPos
            return elp
        }

        private val recycledOrCreate: ExpandableListPosition
            get() {
                var elp: ExpandableListPosition
                synchronized(sPool) {
                    elp = if (sPool.size > 0) {
                        sPool.removeAt(0)
                    } else {
                        return ExpandableListPosition()
                    }
                }
                elp.resetState()
                return elp
            }
    }
}