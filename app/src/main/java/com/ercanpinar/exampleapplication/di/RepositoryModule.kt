package com.ercanpinar.exampleapplication.di

import com.ercanpinar.exampleapplication.data.local.UserDao
import com.ercanpinar.exampleapplication.data.remote.UserApi
import com.ercanpinar.exampleapplication.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userApi: UserApi,
        userDao: UserDao
    ): UserRepository {
        return UserRepository(userApi, userDao)
    }
}