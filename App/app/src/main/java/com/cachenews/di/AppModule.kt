package com.cachenews.di

import android.content.Context
import androidx.room.Room
import com.cachenews.data.local.AppDatabase
import com.cachenews.data.local.ArticleDao
import com.cachenews.data.local.TranslationCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.articleDao()

    @Provides
    @Singleton
    fun provideTranslationCacheDao(db: AppDatabase): TranslationCacheDao = db.translationCacheDao()
}
