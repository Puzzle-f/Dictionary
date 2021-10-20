package com.example.dictionary.viewModel

import androidx.lifecycle.LiveData
import com.example.dictionary.AppState
import com.example.dictionary.interactor.MainInteractor
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (private val interactor: MainInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _mutableLiveData

    fun subscribe(): LiveData<AppState>{
        return liveDataForViewToObserve
    }

    // В этой переменной хранится последнее состояние Activity
    private var appState: AppState? = null

    // Переопределяем метод из BaseViewModel
    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
// Запускаем корутину для асинхронного доступа к серверу с помощью
        // launch
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    // Добавляем suspend
    // withContext(Dispatchers.IO) указывает, что доступ в сеть должен
    // осуществляться через диспетчер IO (который предназначен именно для таких
    // операций), хотя это и не обязательно указывать явно, потому что Retrofit
    // и так делает это благодаря CoroutineCallAdapterFactory(). Это же
    // касается и Room
    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        withContext(Dispatchers.IO){
            val result = interactor.getData(word, isOnline)
//            val mapped = parseSearchResults(result)
            _mutableLiveData.postValue(result)
        }

    }






    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {
            // Данные успешно загружены; сохраняем их и передаем во View (через
            // LiveData). View сама разберётся, как их отображать
            override fun onNext(state: AppState) {
                appState = state
                _mutableLiveData.value = state
            }

            // В случае ошибки передаём её в Activity таким же образом через LiveData
            override fun onError(e: Throwable) {
                _mutableLiveData.value = AppState.Error(e)
            }

            override fun onComplete() {
            }
        }
    }

    override fun handleError(error: Throwable) {
_mutableLiveData.postValue(AppState.Error(null))    }
}
