package com.example.dictionary.interactor

import io.reactivex.Observable

interface IInteractor<T> {
    // Use Сase: получение данных для вывода на экран
    // Используем RxJava
    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}
