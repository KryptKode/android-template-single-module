package com.kryptkode.template.app.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.kryptkode.template.R

/**
 * @author hendrawd on 6/23/16
 */
class CheckableImageView : AppCompatImageView, Checkable {
    constructor(context: Context?) : super(context) {
        init(null)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ){
        init(attrs, defStyle)
    }

    private var mChecked = false

    @SuppressLint("RestrictedApi")
    private fun init(attrs: AttributeSet?, defaultStyle:Int = 0){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CheckableImageView, defaultStyle, 0)

        mChecked = typedArray.getBoolean(R.styleable.CheckableImageView_android_checked, mChecked)
        val buttonTint =
            typedArray.getColor(
                R.styleable.CheckableImageView_civ_buttonTint,
                ContextCompat.getColor(context, R.color.colorOnGradient)
            )
        val button = typedArray.getDrawable(R.styleable.CheckableImageView_android_button)

        setImageDrawable(button)
        supportImageTintList = ColorStateList.valueOf(buttonTint)

        typedArray.recycle()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) View.mergeDrawableStates(
            drawableState,
            CHECKED_STATE_SET
        )
        return drawableState
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            refreshDrawableState()
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val onClickListener =
            OnClickListener { v ->
                toggle()
                l!!.onClick(v)
            }
        super.setOnClickListener(onClickListener)
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}