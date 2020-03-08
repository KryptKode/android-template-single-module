package  com.kryptkode.template.app.utils.expandableadapter.viewholders

import android.view.View
import androidx.databinding.ViewDataBinding
import com.kryptkode.template.app.base.recycler.BaseRecyclerViewHolder
import com.kryptkode.template.app.utils.expandableadapter.listeners.OnGroupClickListener
import com.kryptkode.template.app.utils.expandableadapter.models.Child
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup
import com.kryptkode.template.app.utils.expandableadapter.models.Parent

/**
 * ViewHolder for the [ExpandableGroup.parent] in a [ExpandableGroup]
 *
 * The current implementation does now allow for sub [View] of the parent view to trigger
 * a collapse / expand. *Only* click events on the parent [View] will trigger a collapse or
 * expand
 */
abstract class GroupViewHolder<T : ExpandableGroup<P, C>, P : Parent, C : Child, D : ViewDataBinding>(
    binding: D
) :
    BaseRecyclerViewHolder<T, D>(binding) {
    var listener: OnGroupClickListener? = null

    override fun performBind(item: T?) {
        performBindGroup(item?.parent)
        binding.root.setOnClickListener {
            listener?.onGroupClick(adapterPosition)
        }
    }

    abstract fun performBindGroup(item: P?)
    open fun expand() {}
    open fun collapse() {}
}