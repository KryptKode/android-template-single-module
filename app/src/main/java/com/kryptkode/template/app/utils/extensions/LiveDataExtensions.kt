package com.kryptkode.template.app.utils.extensions

import androidx.lifecycle.*
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.domain.state.DataState
import timber.log.Timber

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) =
    observe(owner, Observer { observer.invoke(it) })

infix fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>) =
    Transformations.switchMap(this, func)

infix fun <X, Y> LiveData<X>.map(func: (X) -> LiveData<Y>) = Transformations.map(this, func)

infix fun <X, Y> LiveData<X>.mapFunc(func: (X) -> Y) = Transformations.map(this, func)

suspend fun <T> LiveDataScope<DataState<T>>.handleError(errorHandler: ErrorHandler, e: Exception) {
    Timber.e(e)
    val error = DataState.Error(errorHandler.getMessageShownToUserFromException(e))
    emit(error)
}
