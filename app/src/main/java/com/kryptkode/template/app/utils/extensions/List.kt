package com.kryptkode.template.app.utils.extensions

/**
 * Created by kryptkode on 3/26/2020.
 */
fun <T> List<T>?.isNotNullOrEmpty(): Boolean {
    return isNullOrEmpty().not()
}