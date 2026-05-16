package com.cachenews.ui.start;

import com.cachenews.data.repository.SettingsRepository;
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
public final class StartViewModel_Factory implements Factory<StartViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public StartViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public StartViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static StartViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new StartViewModel_Factory(settingsRepositoryProvider);
  }

  public static StartViewModel newInstance(SettingsRepository settingsRepository) {
    return new StartViewModel(settingsRepository);
  }
}
