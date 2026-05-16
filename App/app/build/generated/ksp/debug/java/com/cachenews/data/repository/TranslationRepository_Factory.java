package com.cachenews.data.repository;

import com.cachenews.data.translate.TranslationService;
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
public final class TranslationRepository_Factory implements Factory<TranslationRepository> {
  private final Provider<TranslationService> translationServiceProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public TranslationRepository_Factory(Provider<TranslationService> translationServiceProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.translationServiceProvider = translationServiceProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public TranslationRepository get() {
    return newInstance(translationServiceProvider.get(), settingsRepositoryProvider.get());
  }

  public static TranslationRepository_Factory create(
      Provider<TranslationService> translationServiceProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new TranslationRepository_Factory(translationServiceProvider, settingsRepositoryProvider);
  }

  public static TranslationRepository newInstance(TranslationService translationService,
      SettingsRepository settingsRepository) {
    return new TranslationRepository(translationService, settingsRepository);
  }
}
