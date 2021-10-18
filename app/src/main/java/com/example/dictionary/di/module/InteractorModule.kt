package com.example.dictionary.di.module

import com.example.dictionary.di.NAME_LOCAL
import com.example.dictionary.di.NAME_REMOTE
import com.example.dictionary.interactor.MainInteractor
import com.example.dictionary.model.IRepository
import dagger.Module
import dagger.Provides
import com.example.dictionary.model.data.DataModel
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: IRepository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: IRepository<List<DataModel>>
    ) = MainInteractor(repositoryRemote, repositoryLocal)
}
