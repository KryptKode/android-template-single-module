package com.kryptkode.template.app.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher

data class AppDispatchers(
    val network: CoroutineDispatcher,
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher
)