package com.example.dictionary.application

import android.app.Application
import com.example.dictionary.di.koin.application
import com.example.dictionary.di.koin.mainScreen
import dagger.android.DispatchingAndroidInjector
import org.koin.core.context.startKoin
import javax.inject.Inject

// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
// Обратите внимание на dispatchingAndroidInjector и интерфейс Dagger’а
// HasActivityInjector: мы переопределяем его метод activityInjector. Они
// нужны для внедрения зависимостей в Activity
class TranslatorApp : Application() {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

//    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector


    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(application + mainScreen)
        }
    }


}
