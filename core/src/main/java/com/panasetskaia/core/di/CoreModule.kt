package com.panasetskaia.core.di

import android.app.Application
import com.panasetskaia.core.data.PhoneStoreRepositoryImpl
import com.panasetskaia.core.data.database.CartDao
import com.panasetskaia.core.data.database.CartDatabase
import com.panasetskaia.core.data.network.ApiFactory
import com.panasetskaia.core.data.network.ApiService
import com.panasetskaia.core.domain.PhoneStoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface CoreModule {

    @StoreAppScope
    @Binds
    fun bindRepo(repoImpl: PhoneStoreRepositoryImpl): PhoneStoreRepository

    companion object {
        @StoreAppScope
        @Provides
        fun provideDao(application: Application): CartDao {
            return CartDatabase.getInstance(application).cartDao()
        }

        @StoreAppScope
        @Provides
        fun provideApi(): ApiService {
            return ApiFactory.apiService
        }
    }
}