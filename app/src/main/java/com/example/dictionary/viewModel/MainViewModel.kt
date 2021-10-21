package com.example.dictionary.viewModel

import com.example.dictionary.AppState
import com.example.dictionary.interactor.MainInteractor
import io.reactivex.observers.DisposableObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel (
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {
    // В этой переменной хранится последнее состояние Activity
//    private var appState: AppState? = null

    // Переопределяем метод из BaseViewModel
    override fun getData(word: String, isOnline: Boolean) {
        _mutableLiveData.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            startInteractor(word, isOnline)
        }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO){
            val result = interactor.getData(word, isOnline)
//   используем postValue, а не setValue так как находимся в io треде
//            можем переключиться на другой поток
//            withContext(Dispatchers.Main){}
            _mutableLiveData.postValue(result)
        }

    override fun handleError(error: Throwable) {
        _mutableLiveData.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _mutableLiveData.value = AppState.Success(null)
        super.onCleared()
    }

    fun pro():String = "123"

}
