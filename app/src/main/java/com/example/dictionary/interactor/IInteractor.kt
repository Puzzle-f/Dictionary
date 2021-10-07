package com.example.dictionary.interactor

import io.reactivex.Observable

interface IInteractor<T> {
    // Use Сase: получение данных для вывода на экран
    // Используем RxJava
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}
