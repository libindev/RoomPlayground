package com.example.roomplayground.di


import android.content.Context
import com.example.roomplayground.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase = AppDatabase.getDatabase(appContext)
    @Singleton
    @Provides
    fun provideUserInfoDao(db: AppDatabase) = db.userInfoDao()


}
