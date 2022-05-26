package com.example.evsessionboardpoc.data.rx

import io.reactivex.Scheduler

class AppSchedulers(
    val main: Scheduler,
    val computation: Scheduler,
    val io: Scheduler)