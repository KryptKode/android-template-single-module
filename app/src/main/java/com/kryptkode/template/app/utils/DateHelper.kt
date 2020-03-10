package com.kryptkode.template.app.utils

import java.util.*

/**
 * Created by kryptkode on 3/8/2020.
 */
class DateHelper {

    fun now(): Date {
        return Date()
    }

    fun nowInMillis(): Long {
        return now().time
    }
}