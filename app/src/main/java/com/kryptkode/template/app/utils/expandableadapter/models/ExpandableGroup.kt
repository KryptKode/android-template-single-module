package  com.kryptkode.template.app.utils.expandableadapter.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * The backing data object for an [ExpandableGroup]
 */
@Parcelize
open class ExpandableGroup<P : Parent, C : Child>(
    val parent: P,
    val children: List<C>?
) : Parcelable {

    val itemCount: Int
        get() = children?.size ?: 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ExpandableGroup<*, *>) return false

        if (parent != other.parent) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = parent.hashCode()
        result = 31 * result + (children?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "ExpandableGroup(parent=$parent, children=$children)"
    }


}