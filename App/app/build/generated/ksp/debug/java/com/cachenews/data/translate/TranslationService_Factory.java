package com.cachenews.data.translate;

import android.content.Context;
import com.cachenews.data.local.TranslationCacheDao;
import com.cachenews.data.remote.TranslateApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class TranslationService_Factory implements Factory<TranslationService> {
  private final Provider<TranslateApi> translateApiProvider;

  private final Provider<TranslationCacheDao> cacheDaoProvider;

  private final Provider<Context> contextProvider;

  public TranslationService_Factory(Provider<TranslateApi> translateApiProvider,
      Provider<TranslationCacheDao> cacheDaoProvider, Provider<Context> contextProvider) {
    this.translateApiProvider = translateApiProvider;
    this.cacheDaoProvider = cacheDaoProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public TranslationService get() {
    return newInstance(translateApiProvider.get(), cacheDaoProvider.get(), contextProvider.get());
  }

  public static TranslationService_Factory create(Provider<TranslateApi> translateApiProvider,
      Provider<TranslationCacheDao> cacheDaoProvider, Provider<Context> contextProvider) {
    return new TranslationService_Factory(translateApiProvider, cacheDaoProvider, contextProvider);
  }

  public static TranslationService newInstance(TranslateApi translateApi,
      TranslationCacheDao cacheDao, Context context) {
    return new TranslationService(translateApi, cacheDao, context);
  }
}
