package com.kryptkode.template.app.data.domain.usecases

/**
 * Created by kryptkode on 10/23/2019.
 */

abstract class UseCase<T, PARAMS> protected constructor() {

    protected abstract suspend fun buildUseCase(params: PARAMS?): T

    suspend fun execute(params: PARAMS? = null) = buildUseCase(params)
}
