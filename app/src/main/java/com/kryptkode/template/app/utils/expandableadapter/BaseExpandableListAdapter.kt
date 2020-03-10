package com.kryptkode.template.app.utils.expandableadapter

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kryptkode.template.app.utils.expandableadapter.models.Child
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableList
import com.kryptkode.template.app.utils.expandableadapter.models.Parent

/**
 * Created by kryptkode on 3/9/2020.
 */
abstract class BaseExpandableListAdapter
<T : ExpandableGroup<P, C>, P : Parent, C : Child, VH : RecyclerView.ViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>
) : RecyclerView.Adapter<VH>() {

    private val differ: AsyncExpandableListDiffer<T, P, C> = AsyncExpandableListDiffer(
        AdapterListUpdateCallback(this),
        AsyncDifferConfig.Builder(diffCallback).build()
    )
    private val mListener = object : AsyncExpandableListDiffer.ListListener<T, P, C> {
        override fun onCurrentListChanged(
            previousList: ExpandableList<T, P, C>,
            currentList: ExpandableList<T, P, C>
        ) {
            this@BaseExpandableListAdapter.onCurrentListChanged(
                previousList,
                currentList
            )
        }
    }

    init {
        differ.addListListener(mListener)
    }


    fun submitList(list: ExpandableList<T, P, C>?) {
        differ.submitList(list)
    }

    fun submitList(
        list: ExpandableList<T, P, C>?,
        commitCallback: Runnable?
    ) {
        differ.submitList(list, commitCallback)
    }

    protected fun getItem(position: Int): T {
        return differ.currentList.groups[position]
    }

    override fun getItemCount(): Int {
        return differ.currentList.groups.size
    }

    fun getCurrentList(): ExpandableList<T, P, C> {
        return differ.currentList
    }

    fun onCurrentListChanged(
        previousList: ExpandableList<T, P, C>,
        currentList: ExpandableList<T, P, C>
    ) {
    }


}