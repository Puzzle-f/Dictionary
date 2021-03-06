package com.example.dictionary.model.local

import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.DataSource
import com.example.dictionary.model.datasource.RoomDataBaseImplementation
import io.reactivex.Observable


// Для локальных данных используется Room
class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation = RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
