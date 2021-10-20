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

//    корутина всегда запускается в Scope, для этого создаём Scope и указываем для него контекст
    protected val viewModelCoroutineScope = CoroutineScope(
//    указваем поток для работы нашей Корутины
        Dispatchers.Main
//    если внутри Scope у любой корутины произойдёт ошибка, то остальны продолжат работать независимо
//    если не указать, то всё упадёт с ошибкой
            + SupervisorJob()
//                отлавливает ошибки
            + CoroutineExceptionHandler { _, throwable -> handleError(throwable)}

)

    abstract fun getData(word: String, isOnline: Boolean)

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }

    protected fun cancelJob(){
//        завершаем все задачи, которые выполняются внутри Scope
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun handleError(error: Throwable)
}