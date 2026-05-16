package com.cachenews.data.repository;

import com.cachenews.data.local.ArticleDao;
import com.cachenews.data.remote.RssParser;
import com.cachenews.data.remote.RssService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class NewsRepository_Factory implements Factory<NewsRepository> {
  private final Provider<ArticleDao> articleDaoProvider;

  private final Provider<RssService> rssServiceProvider;

  private final Provider<RssParser> rssParserProvider;

  public NewsRepository_Factory(Provider<ArticleDao> articleDaoProvider,
      Provider<RssService> rssServiceProvider, Provider<RssParser> rssParserProvider) {
    this.articleDaoProvider = articleDaoProvider;
    this.rssServiceProvider = rssServiceProvider;
    this.rssParserProvider = rssParserProvider;
  }

  @Override
  public NewsRepository get() {
    return newInstance(articleDaoProvider.get(), rssServiceProvider.get(), rssParserProvider.get());
  }

  public static NewsRepository_Factory create(Provider<ArticleDao> articleDaoProvider,
      Provider<RssService> rssServiceProvider, Provider<RssParser> rssParserProvider) {
    return new NewsRepository_Factory(articleDaoProvider, rssServiceProvider, rssParserProvider);
  }

  public static NewsRepository newInstance(ArticleDao articleDao, RssService rssService,
      RssParser rssParser) {
    return new NewsRepository(articleDao, rssService, rssParser);
  }
}
