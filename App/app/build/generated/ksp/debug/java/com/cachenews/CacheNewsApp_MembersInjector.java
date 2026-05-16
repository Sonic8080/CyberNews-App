package com.cachenews;

import androidx.hilt.work.HiltWorkerFactory;
import com.cachenews.data.repository.NewsRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class CacheNewsApp_MembersInjector implements MembersInjector<CacheNewsApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<NewsRepository> newsRepositoryProvider;

  public CacheNewsApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<NewsRepository> newsRepositoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.newsRepositoryProvider = newsRepositoryProvider;
  }

  public static MembersInjector<CacheNewsApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<NewsRepository> newsRepositoryProvider) {
    return new CacheNewsApp_MembersInjector(workerFactoryProvider, newsRepositoryProvider);
  }

  @Override
  public void injectMembers(CacheNewsApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectNewsRepository(instance, newsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.cachenews.CacheNewsApp.workerFactory")
  public static void injectWorkerFactory(CacheNewsApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.cachenews.CacheNewsApp.newsRepository")
  public static void injectNewsRepository(CacheNewsApp instance, NewsRepository newsRepository) {
    instance.newsRepository = newsRepository;
  }
}
