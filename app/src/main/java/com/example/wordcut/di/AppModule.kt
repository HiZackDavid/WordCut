package com.example.wordcut.di

import com.example.wordcut.data.datasources.AssetDictionaryDataSource
import com.example.wordcut.data.datasources.LocalWordDataSource
import com.example.wordcut.data.repositories.LocalWordRepository
import com.example.wordcut.domain.repositories.WordRepository
import com.example.wordcut.domain.usecases.DeleteLetterUseCase
import com.example.wordcut.domain.usecases.StartGameUseCase
import com.example.wordcut.domain.usecases.SubmitWordUseCase
import com.example.wordcut.domain.usecases.TypeLetterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideLocalWordDataSource(): LocalWordDataSource = LocalWordDataSource()

    @Provides @Singleton
    fun provideWordRepository(repo: LocalWordRepository): WordRepository = repo

    @Provides
    fun provideStartGameUseCase(repo: WordRepository): StartGameUseCase =
        StartGameUseCase(repo)

    @Provides
    fun provideTypeLetterUseCase(): TypeLetterUseCase = TypeLetterUseCase()

    @Provides
    fun provideDeleteLetterUseCase(): DeleteLetterUseCase = DeleteLetterUseCase()

    @Provides
    fun provideSubmitWordUseCase(repo: WordRepository): SubmitWordUseCase =
        SubmitWordUseCase(repo)

}