package com.cachenews.data.gemini;

import com.cachenews.data.repository.SettingsRepository;
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
public final class GeminiService_Factory implements Factory<GeminiService> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public GeminiService_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public GeminiService get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static GeminiService_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new GeminiService_Factory(settingsRepositoryProvider);
  }

  public static GeminiService newInstance(SettingsRepository settingsRepository) {
    return new GeminiService(settingsRepository);
  }
}
