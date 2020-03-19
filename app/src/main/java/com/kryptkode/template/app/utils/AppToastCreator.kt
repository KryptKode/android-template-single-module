package com.kryptkode.template.app.utils

import android.content.Context
import android.widget.Toast

/**
 * Created by kryptkode on 3/1/2020.
 */
class AppToastCreator(private val context: Context) {
    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}