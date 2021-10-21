package com.example.dictionary.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.AppState
import com.example.dictionary.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>(

    protected open val _mutableLiveData: MutableLiveData<T> = MutableLiveData()
) : ViewModel() {

//    место, где запускается Scope
    protected val viewModelCoroutineScope = CoroutineScope(
//    Указываем контекст Scope
//    Указываем поток для выполнения Scope
        Dispatchers.Main
//  чтобы Scope не падал, если у одного корутина произойдёт ошибка
    + SupervisorJob()
//  отлавливаем ошибку в корутине
+ CoroutineExceptionHandler{_, throwable -> handleError(throwable)}

    )

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
       super.onCleared()
        cancelJob()
    }

    protected fun cancelJob() {
//   завершаем все задачи внутри Scope
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)

}