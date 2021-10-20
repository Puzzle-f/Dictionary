package com.example.dictionary.model.datasource

import io.reactivex.Observable

interface DataSource<T> {

//    fun getData(word: String): Observable<T>
   suspend fun getData(word: String): T
}
