package com.example.dictionary.di.koin

import com.example.dictionary.di.NAME_LOCAL
import com.example.dictionary.di.NAME_REMOTE
import com.example.dictionary.interactor.MainInteractor
import com.example.dictionary.model.IRepository
import com.example.dictionary.model.RepositoryImplementation
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.model.datasource.RetrofitImplementation
import com.example.dictionary.viewModel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

// Для удобства создадим две переменные: в одной находятся зависимости,
// используемые во всём приложении, во второй - зависимости конкретного экрана
val application = module {
    single<IRepository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<IRepository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(RetrofitImplementation())
    }
}

// Функция factory сообщает Koin, что эту зависимость нужно создавать каждый
// раз заново, что как раз подходит для Activity и её компонентов.
val mainScreen = module {
    factory {
        MainInteractor(
            repositoryRemote = get(named(NAME_REMOTE)),
            repositoryLocal = get(named(NAME_LOCAL))
        )
    }
    viewModel { MainViewModel(interactor = get()) }
}
