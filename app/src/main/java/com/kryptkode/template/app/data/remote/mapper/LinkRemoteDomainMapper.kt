package com.kryptkode.template.app.data.remote.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Link
import com.kryptkode.template.app.data.remote.response.LinkRemote

/**
 * Created by kryptkode on 3/2/2020.
 */
class LinkRemoteDomainMapper : Mapper<LinkRemote, Link> {

    override fun mapFrom(model: LinkRemote): Link {
        return Link(model.url)
    }

    override fun mapTo(model: Link): LinkRemote {
        return LinkRemote(model.url)
    }
}