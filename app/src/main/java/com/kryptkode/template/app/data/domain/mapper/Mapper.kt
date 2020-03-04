package com.kryptkode.template.app.data.domain.mapper

/**
 * Created by kryptkode on 3/2/2020.
 */
interface Mapper<F, T> {
    fun mapFrom(model: F): T
    fun mapTo(model: T): F
}