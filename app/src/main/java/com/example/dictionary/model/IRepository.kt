package com.example.dictionary.model

import io.reactivex.Observable

interface IRepository<T> {
   suspend fun getData(word:String): T
}