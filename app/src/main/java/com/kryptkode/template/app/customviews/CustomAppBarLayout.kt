package com.kryptkode.template.app.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import java.util.*

/**
 * This file is part of the Universal template
 * For license information, please check the LICENSE
 * file in the root of this project
 *
 * @author Sherdle
 * Copyright 2018
 */
class CustomAppBarLayout : AppBarLayout, OnOffsetChangedListener {
    private var state: State? = null
    private var onStateChangeListeners: MutableList<OnStateChangeListener>? =
        null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    ) {
    }

    val isToolbarHidden: Boolean
        get() = state == State.COLLAPSED

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        check(
            !(layoutParams !is CoordinatorLayout.LayoutParams
                    || parent !is CoordinatorLayout)
        ) { "MyAppBarLayout must be a direct child of CoordinatorLayout." }
        addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(
        appBarLayout: AppBarLayout,
        verticalOffset: Int
    ) {
        state = if (verticalOffset == 0) {
            if (state != State.EXPANDED) {
                notifyListeners(State.EXPANDED)
            }
            State.EXPANDED
        } else if (Math.abs(verticalOffset) >= appBarLayout.totalScrollRange) {
            if (state != State.COLLAPSED) {
                notifyListeners(State.COLLAPSED)
            }
            State.COLLAPSED
        } else {
            if (state != State.IDLE) {
                notifyListeners(State.IDLE)
            }
            State.IDLE
        }
    }

    private fun notifyListeners(state: State) {
        if (onStateChangeListeners == null) return
        for (listener in onStateChangeListeners!!) {
            listener.onStateChange(state)
        }
    }

    fun addOnStateChangeListener(listener: OnStateChangeListener) {
        if (onStateChangeListeners == null) onStateChangeListeners =
            ArrayList()
        onStateChangeListeners!!.add(listener)
    }

    fun removeOnStateChangeListener(listener: OnStateChangeListener) {
        if (onStateChangeListeners == null) return
        onStateChangeListeners!!.remove(listener)
    }

    interface OnStateChangeListener {
        fun onStateChange(toolbarChange: State?)
    }

    enum class State {
        COLLAPSED, EXPANDED, IDLE
    }
}