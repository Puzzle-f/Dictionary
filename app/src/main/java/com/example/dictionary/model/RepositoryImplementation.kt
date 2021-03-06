package com.example.dictionary.model

import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    IRepository<List<DataModel>> {
    // Репозиторий возвращает данные, используя dataSource (локальный или
    // внешний)
    override suspend fun getData(word: String): List<DataModel>{
        return dataSource.getData(word)
    }
}
