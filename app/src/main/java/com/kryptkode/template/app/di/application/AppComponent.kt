package com.kryptkode.template.app.di.application

import com.kryptkode.template.app.App
import com.kryptkode.template.app.di.screen.ScreenComponent
import com.kryptkode.template.app.di.screen.modules.ScreenModule
import dagger.BindsInstance
import dagger.Component

/**
 * Created by kryptkode on 2/19/2020.
 */

@Component(
    modules = [AppModule::class]
)
@ApplicationScope
interface AppComponent {


    fun screenComponent(screenModule: ScreenModule): ScreenComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }
}