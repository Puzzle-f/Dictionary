package com.example.dictionary.model

import io.reactivex.Observable

interface IRepository<T> {
    fun getData(word:String): Observable<T>
}