package com.example.dictionary.view

import com.example.dictionary.AppState

interface IView {
    // View имеет только один метод, в который приходит некое состояние приложения
    fun renderData(appState: AppState)
}
