package com.example.dictionary.interactor

import com.example.dictionary.AppState
import com.example.dictionary.di.NAME_LOCAL
import com.example.dictionary.di.NAME_REMOTE
import com.example.dictionary.interactor.IInteractor
import com.example.dictionary.model.IRepository
import com.example.dictionary.model.data.DataModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Named

class MainInteractor (
    private val repositoryRemote: IRepository<List<DataModel>>,
    private val repositoryLocal: IRepository<List<DataModel>>
)
    : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            repositoryRemote.getData(word).map { AppState.Success(it) }
        } else {
            repositoryLocal.getData(word).map { AppState.Success(it) }
        }
    }
}
