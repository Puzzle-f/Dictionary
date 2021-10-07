package com.example.dictionary.model.local

import geekbrains.ru.translator.model.data.DataModel
import geekbrains.ru.translator.model.datasource.DataSource
import geekbrains.ru.translator.model.datasource.RoomDataBaseImplementation
import io.reactivex.Observable


// Для локальных данных используется Room
class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
