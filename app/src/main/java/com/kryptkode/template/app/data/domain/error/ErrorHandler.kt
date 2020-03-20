package com.kryptkode.template.app.data.domain.error

/**
 * Created by kryptkode on 3/20/2020.
 */
interface ErrorHandler {
    fun getMessageShownToUserFromException(throwable:Throwable): String
}