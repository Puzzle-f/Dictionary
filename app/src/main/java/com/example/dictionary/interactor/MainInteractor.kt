package com.example.dictionary.interactor

import com.example.dictionary.AppState
import com.example.dictionary.model.IRepository
import com.example.dictionary.model.data.DataModel
import io.reactivex.Observable

class MainInteractor(
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
       return AppState.Success(
           if (fromRemoteSource){
               repositoryRemote
           } else {
               repositoryLocal
           }.getData(word)
       )
    }
}
