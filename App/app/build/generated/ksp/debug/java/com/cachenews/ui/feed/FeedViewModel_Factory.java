package com.cachenews.ui.feed;

import android.content.Context;
import androidx.lifecycle.SavedStateHandle;
import com.cachenews.data.repository.NewsRepository;
import com.cachenews.data.repository.SettingsRepository;
import com.cachenews.data.repository.TranslationRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class FeedViewModel_Factory implements Factory<FeedViewModel> {
  private final Provider<NewsRepository> newsRepositoryProvider;

  private final Provider<TranslationRepository> translationRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<Context> contextProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public FeedViewModel_Factory(Provider<NewsRepository> newsRepositoryProvider,
      Provider<TranslationRepository> translationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider, Provider<Context> contextProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.newsRepositoryProvider = newsRepositoryProvider;
    this.translationRepositoryProvider = translationRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.contextProvider = contextProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public FeedViewModel get() {
    return newInstance(newsRepositoryProvider.get(), translationRepositoryProvider.get(), settingsRepositoryProvider.get(), contextProvider.get(), savedStateHandleProvider.get());
  }

  public static FeedViewModel_Factory create(Provider<NewsRepository> newsRepositoryProvider,
      Provider<TranslationRepository> translationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider, Provider<Context> contextProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new FeedViewModel_Factory(newsRepositoryProvider, translationRepositoryProvider, settingsRepositoryProvider, contextProvider, savedStateHandleProvider);
  }

  public static FeedViewModel newInstance(NewsRepository newsRepository,
      TranslationRepository translationRepository, SettingsRepository settingsRepository,
      Context context, SavedStateHandle savedStateHandle) {
    return new FeedViewModel(newsRepository, translationRepository, settingsRepository, context, savedStateHandle);
  }
}
