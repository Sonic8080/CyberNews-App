package com.cachenews.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ArticleDao_Impl implements ArticleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ArticleEntity> __insertionAdapterOfArticleEntity;

  private final EntityDeletionOrUpdateAdapter<ArticleEntity> __updateAdapterOfArticleEntity;

  private final SharedSQLiteStatement __preparedStmtOfSetBookmarked;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldArticles;

  private final SharedSQLiteStatement __preparedStmtOfClearNonBookmarkedArticles;

  public ArticleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfArticleEntity = new EntityInsertionAdapter<ArticleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `articles` (`id`,`title`,`description`,`content`,`imageUrl`,`sourceUrl`,`sourceName`,`source`,`category`,`publishedAt`,`isBookmarked`,`isNotified`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ArticleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getContent());
        if (entity.getImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImageUrl());
        }
        statement.bindString(6, entity.getSourceUrl());
        statement.bindString(7, entity.getSourceName());
        statement.bindString(8, entity.getSource());
        statement.bindString(9, entity.getCategory());
        statement.bindLong(10, entity.getPublishedAt());
        final int _tmp = entity.isBookmarked() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.isNotified() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
      }
    };
    this.__updateAdapterOfArticleEntity = new EntityDeletionOrUpdateAdapter<ArticleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `articles` SET `id` = ?,`title` = ?,`description` = ?,`content` = ?,`imageUrl` = ?,`sourceUrl` = ?,`sourceName` = ?,`source` = ?,`category` = ?,`publishedAt` = ?,`isBookmarked` = ?,`isNotified` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ArticleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        statement.bindString(4, entity.getContent());
        if (entity.getImageUrl() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getImageUrl());
        }
        statement.bindString(6, entity.getSourceUrl());
        statement.bindString(7, entity.getSourceName());
        statement.bindString(8, entity.getSource());
        statement.bindString(9, entity.getCategory());
        statement.bindLong(10, entity.getPublishedAt());
        final int _tmp = entity.isBookmarked() ? 1 : 0;
        statement.bindLong(11, _tmp);
        final int _tmp_1 = entity.isNotified() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        statement.bindString(13, entity.getId());
      }
    };
    this.__preparedStmtOfSetBookmarked = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE articles SET isBookmarked = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldArticles = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM articles WHERE isBookmarked = 0 AND publishedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearNonBookmarkedArticles = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM articles WHERE isBookmarked = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insertArticles(final List<ArticleEntity> articles,
      final Continuation<? super List<Long>> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<List<Long>>() {
      @Override
      @NonNull
      public List<Long> call() throws Exception {
        __db.beginTransaction();
        try {
          final List<Long> _result = __insertionAdapterOfArticleEntity.insertAndReturnIdsList(articles);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateArticle(final ArticleEntity article,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfArticleEntity.handle(article);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setBookmarked(final String id, final boolean bookmarked,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetBookmarked.acquire();
        int _argIndex = 1;
        final int _tmp = bookmarked ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindString(_argIndex, id);
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
          __preparedStmtOfSetBookmarked.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldArticles(final long beforeTimestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldArticles.acquire();
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
          __preparedStmtOfDeleteOldArticles.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearNonBookmarkedArticles(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearNonBookmarkedArticles.acquire();
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
          __preparedStmtOfClearNonBookmarkedArticles.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ArticleEntity>> getArticlesByCategory(final String category) {
    final String _sql = "SELECT * FROM articles WHERE category = ? ORDER BY publishedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ArticleEntity>> getArticlesByCategoryAndTime(final String category,
      final long fromTimestamp) {
    final String _sql = "\n"
            + "        SELECT * FROM articles \n"
            + "        WHERE category = ? \n"
            + "        AND publishedAt >= ? \n"
            + "        ORDER BY publishedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindLong(_argIndex, fromTimestamp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ArticleEntity>> getArticlesByCategorySourceAndTime(final String category,
      final String sourceName, final long fromTimestamp) {
    final String _sql = "\n"
            + "        SELECT * FROM articles \n"
            + "        WHERE category = ? \n"
            + "        AND sourceName = ? \n"
            + "        AND publishedAt >= ? \n"
            + "        ORDER BY publishedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindString(_argIndex, sourceName);
    _argIndex = 3;
    _statement.bindLong(_argIndex, fromTimestamp);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ArticleEntity>> getArticlesByCategoryAndSource(final String category,
      final String sourceName) {
    final String _sql = "\n"
            + "        SELECT * FROM articles \n"
            + "        WHERE category = ? \n"
            + "        AND sourceName = ? \n"
            + "        ORDER BY publishedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindString(_argIndex, sourceName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getArticleById(final String id,
      final Continuation<? super ArticleEntity> $completion) {
    final String _sql = "SELECT * FROM articles WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ArticleEntity>() {
      @Override
      @Nullable
      public ArticleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final ArticleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _result = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
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
  public Flow<List<ArticleEntity>> getBookmarkedArticles() {
    final String _sql = "SELECT * FROM articles WHERE isBookmarked = 1 ORDER BY publishedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUnnotifiedArticles(final Continuation<? super List<ArticleEntity>> $completion) {
    final String _sql = "SELECT * FROM articles WHERE isNotified = 0 ORDER BY publishedAt DESC LIMIT 20";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
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
  public Object articleExists(final String url, final Continuation<? super Boolean> $completion) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM articles WHERE sourceUrl = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, url);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
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
  public Object getArticleCount(final String category,
      final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM articles WHERE category = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
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

  @Override
  public Flow<List<ArticleEntity>> searchArticles(final String category, final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM articles \n"
            + "        WHERE category = ? \n"
            + "        AND (title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%')\n"
            + "        ORDER BY publishedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    _argIndex = 3;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<ArticleEntity>> searchBookmarkedArticles(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM articles \n"
            + "        WHERE isBookmarked = 1\n"
            + "        AND (title LIKE '%' || ? || '%' OR description LIKE '%' || ? || '%')\n"
            + "        ORDER BY publishedAt DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"articles"}, new Callable<List<ArticleEntity>>() {
      @Override
      @NonNull
      public List<ArticleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfIsBookmarked = CursorUtil.getColumnIndexOrThrow(_cursor, "isBookmarked");
          final int _cursorIndexOfIsNotified = CursorUtil.getColumnIndexOrThrow(_cursor, "isNotified");
          final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ArticleEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final String _tmpSourceUrl;
            _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSource;
            _tmpSource = _cursor.getString(_cursorIndexOfSource);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final boolean _tmpIsBookmarked;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsBookmarked);
            _tmpIsBookmarked = _tmp != 0;
            final boolean _tmpIsNotified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsNotified);
            _tmpIsNotified = _tmp_1 != 0;
            _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpImageUrl,_tmpSourceUrl,_tmpSourceName,_tmpSource,_tmpCategory,_tmpPublishedAt,_tmpIsBookmarked,_tmpIsNotified);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalArticleCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM articles";
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

  @Override
  public Object markAsNotified(final List<String> ids,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
        _stringBuilder.append("UPDATE articles SET isNotified = 1 WHERE id IN (");
        final int _inputSize = ids.size();
        StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
        _stringBuilder.append(")");
        final String _sql = _stringBuilder.toString();
        final SupportSQLiteStatement _stmt = __db.compileStatement(_sql);
        int _argIndex = 1;
        for (String _item : ids) {
          _stmt.bindString(_argIndex, _item);
          _argIndex++;
        }
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
