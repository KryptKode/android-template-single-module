package com.kryptkode.template.app.di.screen.modules.mapper

import com.kryptkode.template.app.di.screen.modules.mapper.local.LocalMapperModule
import com.kryptkode.template.app.di.screen.modules.mapper.remote.RemoteMapperModule
import com.kryptkode.template.app.di.screen.modules.mapper.view.ViewMapperModules
import dagger.Module

/**
 * Created by kryptkode on 3/3/2020.
 */
@Module(includes = [ViewMapperModules::class, LocalMapperModule::class, RemoteMapperModule::class])
class MapperModule