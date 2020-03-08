package  com.kryptkode.template.app.utils.expandableadapter.viewholders

import androidx.databinding.ViewDataBinding
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.utils.expandableadapter.models.Child
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup
import com.kryptkode.template.app.utils.expandableadapter.models.Parent

/**
 * ViewHolder for [ExpandableGroup.children]
 */
abstract class ChildViewHolder<T : ExpandableGroup<P, C>, P : Parent, C : Child, D>(binding: D) :
    BaseRecyclerViewHolder<T, D>(binding) where D : ViewDataBinding {

    override fun performBind(item: T?) {
        performBind(item, adapterPosition)
    }

    fun performBind(item: T?, childPosition: Int) {
        performBind(item?.children?.get(childPosition))
    }

    abstract fun performBind(child: Child?)
}