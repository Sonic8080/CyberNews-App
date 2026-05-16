package com.cachenews.data.remote;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class RssParser_Factory implements Factory<RssParser> {
  @Override
  public RssParser get() {
    return newInstance();
  }

  public static RssParser_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RssParser newInstance() {
    return new RssParser();
  }

  private static final class InstanceHolder {
    private static final RssParser_Factory INSTANCE = new RssParser_Factory();
  }
}
