package com.aion.rickandmortypt.core.di

import android.content.Context
import androidx.room.Room
import com.aion.rickandmortypt.core.data.database.AppDatabase
import com.aion.rickandmortypt.core.data.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val DATABASE_NAME = "app_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.getCharacterDao()

    @Singleton
    @Provides
    fun provideEpisodeDao(db: AppDatabase) = db.getEpisodeDao()

    @Singleton
    @Provides
    fun provideLocationDao(db: AppDatabase) = db.getLocationDao()
}