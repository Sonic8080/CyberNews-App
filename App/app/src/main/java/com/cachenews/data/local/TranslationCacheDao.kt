package com.cachenews.data.local

import androidx.room.*

@Dao
interface TranslationCacheDao {

    @Query("SELECT * FROM translation_cache WHERE textHash = :hash AND targetLanguage = :language LIMIT 1")
    suspend fun getTranslation(hash: String, language: String): TranslationCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTranslation(entity: TranslationCacheEntity)

    @Query("DELETE FROM translation_cache WHERE createdAt < :beforeTimestamp")
    suspend fun clearOldCache(beforeTimestamp: Long)

    @Query("SELECT COUNT(*) FROM translation_cache")
    suspend fun getCacheSize(): Int
}
