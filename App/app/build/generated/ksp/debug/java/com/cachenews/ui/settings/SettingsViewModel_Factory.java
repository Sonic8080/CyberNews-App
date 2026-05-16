package com.cachenews.ui.settings;

import com.cachenews.data.repository.SettingsRepository;
import com.cachenews.notification.NotificationHelper;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<NotificationHelper> notificationHelperProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public SettingsViewModel_Factory(Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.notificationHelperProvider = notificationHelperProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(notificationHelperProvider.get(), settingsRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new SettingsViewModel_Factory(notificationHelperProvider, settingsRepositoryProvider);
  }

  public static SettingsViewModel newInstance(NotificationHelper notificationHelper,
      SettingsRepository settingsRepository) {
    return new SettingsViewModel(notificationHelper, settingsRepository);
  }
}
