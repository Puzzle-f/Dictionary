package com.example.dictionary.presenter

import com.example.dictionary.AppState
import com.example.dictionary.view.IView

interface IPresenter<T : AppState, V : IView> {

    fun attachView(view: V)

    fun detachView(view: V)
    // Получение данных с флагом isOnline(из Интернета или нет)
    fun getData(word: String, isOnline: Boolean)
}
