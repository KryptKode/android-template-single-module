package com.kryptkode.template.app.data.remote.mapper


/**
 * Created by kryptkode on 3/3/2020.
 */
class RemoteMappers(
    val card: CardRemoteDomainMapper,
    val category: CategoryRemoteDomainMapper,
    val subcategory: SubcategoryRemoteDomainMapper,
    val link: LinkRemoteDomainMapper
)