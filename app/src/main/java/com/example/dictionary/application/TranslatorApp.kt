package com.example.dictionary.application

import android.app.Application
import com.example.dictionary.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
class TranslatorApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }


}
