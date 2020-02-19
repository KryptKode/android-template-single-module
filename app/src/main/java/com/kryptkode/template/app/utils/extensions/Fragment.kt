package com.kryptkode.template.app.utils.extensions

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment

/**
 * Created by kryptkode on 11/17/2019.
 */
fun Fragment.getVectorDrawable(@DrawableRes resId: Int): Drawable? {
    return context?.let {
        AppCompatResources.getDrawable(it, resId)
    }
}