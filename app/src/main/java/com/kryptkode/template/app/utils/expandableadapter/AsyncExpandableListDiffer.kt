package com.kryptkode.template.app.utils.expandableadapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.DiffUtil.DiffResult
import com.kryptkode.template.app.utils.expandableadapter.models.Child
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableGroup
import com.kryptkode.template.app.utils.expandableadapter.models.ExpandableList
import com.kryptkode.template.app.utils.expandableadapter.models.Parent
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.Executor

class AsyncExpandableListDiffer<T : ExpandableGroup<P, C>, P : Parent, C : Child> @SuppressLint("RestrictedApi") constructor(
    private val mUpdateCallback: ListUpdateCallback,
    val mConfig: AsyncDifferConfig<T>
) {
    var mMainThreadExecutor: Executor? = null

    private class MainThreadExecutor internal constructor() : Executor {
        val mHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mHandler.post(command)
        }
    }

    interface ListListener<T : ExpandableGroup<P, C>, P : Parent, C : Child> {
        fun onCurrentListChanged(
            previousList: ExpandableList<T, P, C>,
            currentList: ExpandableList<T, P, C>
        )
    }

    private val mListeners: MutableList<ListListener<T, P, C>> =
        CopyOnWriteArrayList()

    constructor(
        adapter: RecyclerView.Adapter<*>,
        diffCallback: DiffUtil.ItemCallback<T>
    ) : this(
        AdapterListUpdateCallback(adapter),
        AsyncDifferConfig.Builder<T>(diffCallback).build()
    ) {
    }

    private var mList: ExpandableList<T, P, C>? = null

    var currentList: ExpandableList<T, P, C> = ExpandableList()
        private set

    // Max generation of currently scheduled runnable
    var mMaxScheduledGeneration = 0

    @JvmOverloads
    fun submitList(
        newList: ExpandableList<T, P, C>?,
        commitCallback: Runnable? = null
    ) {
        // incrementing generation means any currently-running diffs are discarded when they finish
        val runGeneration = ++mMaxScheduledGeneration
        if (newList === mList) {
            // nothing to do (Note - still had to inc generation, since may have ongoing work)
            commitCallback?.run()
            return
        }
        val previousList = currentList

        // fast simple remove all
        if (newList == null) {
            val countRemoved = mList?.groups?.size ?: 0
            mList = null
            currentList = ExpandableList()
            // notify last, after list is updated
            mUpdateCallback.onRemoved(0, countRemoved)
            onCurrentListChanged(previousList, commitCallback)
            return
        }

        // fast simple first insert
        if (mList == null) {
            mList = newList
            currentList = newList
            // notify last, after list is updated
            mUpdateCallback.onInserted(0, newList.groups.size)
            onCurrentListChanged(previousList, commitCallback)
            return
        }
        val oldList: ExpandableList<T, P, C> = mList!!
        mConfig.backgroundThreadExecutor.execute {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.groups.size
                }

                override fun getNewListSize(): Int {
                    return newList.groups.size
                }

                override fun areItemsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem: T? = oldList.groups[oldItemPosition]
                    val newItem: T? = newList.groups[newItemPosition]
                    return if (oldItem != null && newItem != null) {
                        mConfig.diffCallback.areItemsTheSame(oldItem, newItem)
                    } else oldItem == null && newItem == null
                    // If both items are null we consider them the same.
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val oldItem: T? = oldList.groups[oldItemPosition]
                    val newItem: T? = newList.groups[newItemPosition]
                    if (oldItem != null && newItem != null) {
                        return mConfig.diffCallback.areContentsTheSame(oldItem, newItem)
                    }
                    if (oldItem == null && newItem == null) {
                        return true
                    }
                    throw AssertionError()
                }

                override fun getChangePayload(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Any? {
                    val oldItem: T? = oldList.groups[oldItemPosition]
                    val newItem: T? = newList.groups[newItemPosition]
                    if (oldItem != null && newItem != null) {
                        return mConfig.diffCallback.getChangePayload(oldItem, newItem)
                    }
                    throw AssertionError()
                }
            })
            mMainThreadExecutor!!.execute {
                if (mMaxScheduledGeneration == runGeneration) {
                    latchList(newList, result, commitCallback)
                }
            }
        }
    }

    fun  /* synthetic access */latchList(
        newList: ExpandableList<T, P, C>,
        diffResult: DiffResult,
        commitCallback: Runnable?
    ) {
        val previousList = currentList
        mList = newList
        // notify last, after list is updated
        currentList = newList
        diffResult.dispatchUpdatesTo(mUpdateCallback)
        onCurrentListChanged(previousList, commitCallback)
    }

    private fun onCurrentListChanged(
        previousList: ExpandableList<T, P, C>,
        commitCallback: Runnable?
    ) {
        // current list is always mReadOnlyList
        for (listener in mListeners) {
            listener.onCurrentListChanged(previousList, currentList)
        }
        commitCallback?.run()
    }

    fun addListListener(listener: ListListener<T, P, C>) {
        mListeners.add(listener)
    }

    fun removeListListener(listener: ListListener<T, P, C>) {
        mListeners.remove(listener)
    }

    companion object {
        // TODO: use MainThreadExecutor from supportlib once one exists
        private val sMainThreadExecutor: Executor =
            MainThreadExecutor()
    }

    init {
        mMainThreadExecutor = if (mConfig.mainThreadExecutor != null) {
            mConfig.mainThreadExecutor
        } else {
            sMainThreadExecutor
        }
    }
}