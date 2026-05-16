package com.cachenews.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ArticleDao _articleDao;

  private volatile TranslationCacheDao _translationCacheDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `articles` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `content` TEXT NOT NULL, `imageUrl` TEXT, `sourceUrl` TEXT NOT NULL, `sourceName` TEXT NOT NULL, `source` TEXT NOT NULL, `category` TEXT NOT NULL, `publishedAt` INTEGER NOT NULL, `isBookmarked` INTEGER NOT NULL, `isNotified` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_articles_sourceUrl` ON `articles` (`sourceUrl`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_articles_category` ON `articles` (`category`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_articles_sourceName` ON `articles` (`sourceName`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_articles_publishedAt` ON `articles` (`publishedAt`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `translation_cache` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `textHash` TEXT NOT NULL, `originalText` TEXT NOT NULL, `translatedText` TEXT NOT NULL, `targetLanguage` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_translation_cache_textHash_targetLanguage` ON `translation_cache` (`textHash`, `targetLanguage`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '465e2160b1c1a865a99d0883fc4d618f')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `articles`");
        db.execSQL("DROP TABLE IF EXISTS `translation_cache`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsArticles = new HashMap<String, TableInfo.Column>(12);
        _columnsArticles.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("imageUrl", new TableInfo.Column("imageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("sourceUrl", new TableInfo.Column("sourceUrl", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("sourceName", new TableInfo.Column("sourceName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("publishedAt", new TableInfo.Column("publishedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("isBookmarked", new TableInfo.Column("isBookmarked", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsArticles.put("isNotified", new TableInfo.Column("isNotified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysArticles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesArticles = new HashSet<TableInfo.Index>(4);
        _indicesArticles.add(new TableInfo.Index("index_articles_sourceUrl", true, Arrays.asList("sourceUrl"), Arrays.asList("ASC")));
        _indicesArticles.add(new TableInfo.Index("index_articles_category", false, Arrays.asList("category"), Arrays.asList("ASC")));
        _indicesArticles.add(new TableInfo.Index("index_articles_sourceName", false, Arrays.asList("sourceName"), Arrays.asList("ASC")));
        _indicesArticles.add(new TableInfo.Index("index_articles_publishedAt", false, Arrays.asList("publishedAt"), Arrays.asList("ASC")));
        final TableInfo _infoArticles = new TableInfo("articles", _columnsArticles, _foreignKeysArticles, _indicesArticles);
        final TableInfo _existingArticles = TableInfo.read(db, "articles");
        if (!_infoArticles.equals(_existingArticles)) {
          return new RoomOpenHelper.ValidationResult(false, "articles(com.cachenews.data.local.ArticleEntity).\n"
                  + " Expected:\n" + _infoArticles + "\n"
                  + " Found:\n" + _existingArticles);
        }
        final HashMap<String, TableInfo.Column> _columnsTranslationCache = new HashMap<String, TableInfo.Column>(6);
        _columnsTranslationCache.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationCache.put("textHash", new TableInfo.Column("textHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationCache.put("originalText", new TableInfo.Column("originalText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationCache.put("translatedText", new TableInfo.Column("translatedText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationCache.put("targetLanguage", new TableInfo.Column("targetLanguage", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTranslationCache.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTranslationCache = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTranslationCache = new HashSet<TableInfo.Index>(1);
        _indicesTranslationCache.add(new TableInfo.Index("index_translation_cache_textHash_targetLanguage", true, Arrays.asList("textHash", "targetLanguage"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoTranslationCache = new TableInfo("translation_cache", _columnsTranslationCache, _foreignKeysTranslationCache, _indicesTranslationCache);
        final TableInfo _existingTranslationCache = TableInfo.read(db, "translation_cache");
        if (!_infoTranslationCache.equals(_existingTranslationCache)) {
          return new RoomOpenHelper.ValidationResult(false, "translation_cache(com.cachenews.data.local.TranslationCacheEntity).\n"
                  + " Expected:\n" + _infoTranslationCache + "\n"
                  + " Found:\n" + _existingTranslationCache);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "465e2160b1c1a865a99d0883fc4d618f", "45ae82c6dd0bd6a74c4f59042448011d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "articles","translation_cache");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `articles`");
      _db.execSQL("DELETE FROM `translation_cache`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ArticleDao.class, ArticleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TranslationCacheDao.class, TranslationCacheDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ArticleDao articleDao() {
    if (_articleDao != null) {
      return _articleDao;
    } else {
      synchronized(this) {
        if(_articleDao == null) {
          _articleDao = new ArticleDao_Impl(this);
        }
        return _articleDao;
      }
    }
  }

  @Override
  public TranslationCacheDao translationCacheDao() {
    if (_translationCacheDao != null) {
      return _translationCacheDao;
    } else {
      synchronized(this) {
        if(_translationCacheDao == null) {
          _translationCacheDao = new TranslationCacheDao_Impl(this);
        }
        return _translationCacheDao;
      }
    }
  }
}
