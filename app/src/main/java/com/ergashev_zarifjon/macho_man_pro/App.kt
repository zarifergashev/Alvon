package com.ergashev_zarifjon.macho_man_pro

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.ergashev_zarifjon.macho_man_pro.data.AppDatabas
import io.fabric.sdk.android.Fabric


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}