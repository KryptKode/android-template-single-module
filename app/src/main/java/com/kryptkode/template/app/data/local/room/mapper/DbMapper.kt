package com.kryptkode.template.app.data.local.room.mapper

interface DbMapper<C, E> {

    fun mapFromCached(type: C): E

    fun mapToCached(type: E): C

}