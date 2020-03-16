package com.kryptkode.template.app.data.repo

import android.content.Context
import com.kryptkode.template.R
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by kryptkode on 3/15/2020.
 */
class ErrorHandler (private val context: Context) {

    fun getMessageShownToUserFromException(throwable:Throwable): String {
        return when(throwable){
            is IOException -> getString(R.string.internet_error_message)
            is HttpException -> throwable.message()
            else -> getString(R.string.general_error_message)
        }
    }

    private fun getString(resId:Int): String {
        return context.getString(resId)
    }
}