package com.example.dictionary.model.retrofit

import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.DataSource
import com.example.dictionary.model.datasource.RetrofitImplementation
import io.reactivex.Observable

// Для получения внешних данных мы будем использовать Retrofit
class DataSourceRemote(private val remoteProvider: RetrofitImplementation = RetrofitImplementation()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}