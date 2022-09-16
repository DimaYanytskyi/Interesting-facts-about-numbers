package com.dima.test.di

import android.app.Application
import androidx.room.Room
import com.dima.test.common.Constants.BASE_URL
import com.dima.test.common.Constants.DATABASE_NAME
import com.dima.test.data.data_source.local.NumbersDao
import com.dima.test.data.data_source.local.NumbersDatabase
import com.dima.test.data.data_source.remote.NumbersApi
import com.dima.test.data.repository.NumberRepositoryImpl
import com.dima.test.domain.interactor.NumberInteractor
import com.dima.test.domain.interactor.NumberInteractorImpl
import com.dima.test.domain.repository.NumberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNumbersDatabase(app: Application) : NumbersDatabase {
        return Room.databaseBuilder(
            app,
            NumbersDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNumberApi() : NumbersApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NumbersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNumberRepository(api: NumbersApi, db: NumbersDatabase) : NumberRepository {
        return NumberRepositoryImpl(api, db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNumberInteractor(repository: NumberRepository) : NumberInteractor {
        return NumberInteractorImpl(repository)
    }
}