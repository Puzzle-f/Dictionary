package com.example.dictionary.model

import io.reactivex.Observable

interface IDataSource<T> {

    fun getData(word: String): Observable<T>
}
