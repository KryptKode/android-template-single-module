package com.kryptkode.template.app.data.local.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Link

/**
 * Created by kryptkode on 3/2/2020.
 */
class LinkLocalDomainMapper : Mapper<String, Link> {

    override fun mapFrom(model: String): Link {
        return Link(model)
    }

    override fun mapTo(model: Link): String {
        return model.url
    }
}