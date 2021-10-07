package geekbrains.ru.translator.view.main

import com.example.dictionary.AppState
import com.example.dictionary.interactor.IInteractor
import com.example.dictionary.model.IRepository
import geekbrains.ru.translator.model.data.DataModel
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: IRepository<List<DataModel>>,
    private val localRepository: IRepository<List<DataModel>>
) : IInteractor<AppState> {

    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}
