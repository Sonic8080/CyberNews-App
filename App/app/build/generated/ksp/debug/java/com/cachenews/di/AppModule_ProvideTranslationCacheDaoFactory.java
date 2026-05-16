package com.cachenews.di;

import com.cachenews.data.local.AppDatabase;
import com.cachenews.data.local.TranslationCacheDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_ProvideTranslationCacheDaoFactory implements Factory<TranslationCacheDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideTranslationCacheDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TranslationCacheDao get() {
    return provideTranslationCacheDao(dbProvider.get());
  }

  public static AppModule_ProvideTranslationCacheDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideTranslationCacheDaoFactory(dbProvider);
  }

  public static TranslationCacheDao provideTranslationCacheDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTranslationCacheDao(db));
  }
}
