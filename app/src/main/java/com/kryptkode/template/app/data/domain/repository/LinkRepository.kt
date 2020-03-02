package com.kryptkode.template.app.data.domain.repository

import com.kryptkode.template.app.data.domain.model.Link

/**
 * Created by kryptkode on 2/19/2020.
 */
interface LinkRepository {
    fun getLink(): Link
}