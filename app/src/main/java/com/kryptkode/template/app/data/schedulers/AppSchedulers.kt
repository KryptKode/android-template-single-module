package com.kryptkode.template.app.data.schedulers

import io.reactivex.Scheduler


data class AppSchedulers(
    val network: Scheduler,
    val io: Scheduler,
    val main: Scheduler
)