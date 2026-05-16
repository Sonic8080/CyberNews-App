package com.cachenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class, TranslationCacheEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun translationCacheDao(): TranslationCacheDao

    companion object {
        const val DATABASE_NAME = "cachenews_db"
    }
}
