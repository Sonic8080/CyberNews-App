package com.cachenews.di;

import com.cachenews.data.remote.RssService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class NetworkModule_ProvideRssServiceFactory implements Factory<RssService> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideRssServiceFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public RssService get() {
    return provideRssService(retrofitProvider.get());
  }

  public static NetworkModule_ProvideRssServiceFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideRssServiceFactory(retrofitProvider);
  }

  public static RssService provideRssService(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRssService(retrofit));
  }
}
