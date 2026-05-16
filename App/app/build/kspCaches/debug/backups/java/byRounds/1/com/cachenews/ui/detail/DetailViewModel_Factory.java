package com.cachenews.ui.detail;

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
public final class DetailViewModel_Factory implements Factory<DetailViewModel> {
  private final Provider<NewsRepository> newsRepositoryProvider;

  private final Provider<TranslationRepository> translationRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public DetailViewModel_Factory(Provider<NewsRepository> newsRepositoryProvider,
      Provider<TranslationRepository> translationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.newsRepositoryProvider = newsRepositoryProvider;
    this.translationRepositoryProvider = translationRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public DetailViewModel get() {
    return newInstance(newsRepositoryProvider.get(), translationRepositoryProvider.get(), settingsRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static DetailViewModel_Factory create(Provider<NewsRepository> newsRepositoryProvider,
      Provider<TranslationRepository> translationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new DetailViewModel_Factory(newsRepositoryProvider, translationRepositoryProvider, settingsRepositoryProvider, savedStateHandleProvider);
  }

  public static DetailViewModel newInstance(NewsRepository newsRepository,
      TranslationRepository translationRepository, SettingsRepository settingsRepository,
      SavedStateHandle savedStateHandle) {
    return new DetailViewModel(newsRepository, translationRepository, settingsRepository, savedStateHandle);
  }
}
