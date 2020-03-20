package com.kryptkode.template.app.data.domain.state

/**
 * Created by kryptkode on 10/23/2019.
 */

sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    class Success<out T>(val data: T) : DataState<T>()
    class Error(val errorMessage: String) : DataState<Nothing>()
}
val DataState<*>.succeeded
    get() = this is DataState.Success && data != null

fun <T> DataState<T>.successOr(fallback: T): T {
    return (this as? DataState.Success<T>)?.data ?: fallback
}