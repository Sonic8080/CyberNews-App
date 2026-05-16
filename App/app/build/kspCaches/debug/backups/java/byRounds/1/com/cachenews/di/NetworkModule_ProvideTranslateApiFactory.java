package com.cachenews.di;

import com.cachenews.data.remote.TranslateApi;
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
public final class NetworkModule_ProvideTranslateApiFactory implements Factory<TranslateApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideTranslateApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public TranslateApi get() {
    return provideTranslateApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideTranslateApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideTranslateApiFactory(retrofitProvider);
  }

  public static TranslateApi provideTranslateApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideTranslateApi(retrofit));
  }
}
