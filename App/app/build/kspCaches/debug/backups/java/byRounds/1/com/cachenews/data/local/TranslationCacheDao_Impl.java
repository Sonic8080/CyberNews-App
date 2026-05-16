package com.cachenews.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TranslationCacheDao_Impl implements TranslationCacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TranslationCacheEntity> __insertionAdapterOfTranslationCacheEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearOldCache;

  public TranslationCacheDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTranslationCacheEntity = new EntityInsertionAdapter<TranslationCacheEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `translation_cache` (`id`,`textHash`,`originalText`,`translatedText`,`targetLanguage`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TranslationCacheEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTextHash());
        statement.bindString(3, entity.getOriginalText());
        statement.bindString(4, entity.getTranslatedText());
        statement.bindString(5, entity.getTargetLanguage());
        statement.bindLong(6, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfClearOldCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM translation_cache WHERE createdAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTranslation(final TranslationCacheEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTranslationCacheEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearOldCache(final long beforeTimestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearOldCache.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, beforeTimestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearOldCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getTranslation(final String hash, final String language,
      final Continuation<? super TranslationCacheEntity> $completion) {
    final String _sql = "SELECT * FROM translation_cache WHERE textHash = ? AND targetLanguage = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, hash);
    _argIndex = 2;
    _statement.bindString(_argIndex, language);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TranslationCacheEntity>() {
      @Override
      @Nullable
      public TranslationCacheEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTextHash = CursorUtil.getColumnIndexOrThrow(_cursor, "textHash");
          final int _cursorIndexOfOriginalText = CursorUtil.getColumnIndexOrThrow(_cursor, "originalText");
          final int _cursorIndexOfTranslatedText = CursorUtil.getColumnIndexOrThrow(_cursor, "translatedText");
          final int _cursorIndexOfTargetLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "targetLanguage");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final TranslationCacheEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTextHash;
            _tmpTextHash = _cursor.getString(_cursorIndexOfTextHash);
            final String _tmpOriginalText;
            _tmpOriginalText = _cursor.getString(_cursorIndexOfOriginalText);
            final String _tmpTranslatedText;
            _tmpTranslatedText = _cursor.getString(_cursorIndexOfTranslatedText);
            final String _tmpTargetLanguage;
            _tmpTargetLanguage = _cursor.getString(_cursorIndexOfTargetLanguage);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new TranslationCacheEntity(_tmpId,_tmpTextHash,_tmpOriginalText,_tmpTranslatedText,_tmpTargetLanguage,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCacheSize(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM translation_cache";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
