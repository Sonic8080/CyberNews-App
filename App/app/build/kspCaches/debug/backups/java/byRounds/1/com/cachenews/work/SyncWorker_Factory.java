package com.cachenews.work;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.cachenews.data.repository.NewsRepository;
import com.cachenews.notification.NotificationHelper;
import dagger.internal.DaggerGenerated;
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
public final class SyncWorker_Factory {
  private final Provider<NewsRepository> newsRepositoryProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public SyncWorker_Factory(Provider<NewsRepository> newsRepositoryProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.newsRepositoryProvider = newsRepositoryProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public SyncWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, newsRepositoryProvider.get(), notificationHelperProvider.get());
  }

  public static SyncWorker_Factory create(Provider<NewsRepository> newsRepositoryProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new SyncWorker_Factory(newsRepositoryProvider, notificationHelperProvider);
  }

  public static SyncWorker newInstance(Context context, WorkerParameters workerParams,
      NewsRepository newsRepository, NotificationHelper notificationHelper) {
    return new SyncWorker(context, workerParams, newsRepository, notificationHelper);
  }
}
