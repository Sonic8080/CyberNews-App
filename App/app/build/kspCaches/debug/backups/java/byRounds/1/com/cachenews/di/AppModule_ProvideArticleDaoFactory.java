package com.cachenews.di;

import com.cachenews.data.local.AppDatabase;
import com.cachenews.data.local.ArticleDao;
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
public final class AppModule_ProvideArticleDaoFactory implements Factory<ArticleDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideArticleDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ArticleDao get() {
    return provideArticleDao(dbProvider.get());
  }

  public static AppModule_ProvideArticleDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideArticleDaoFactory(dbProvider);
  }

  public static ArticleDao provideArticleDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideArticleDao(db));
  }
}
