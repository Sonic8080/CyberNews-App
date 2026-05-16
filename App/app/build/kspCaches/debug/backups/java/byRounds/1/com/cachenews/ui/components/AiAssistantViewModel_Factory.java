package com.cachenews.ui.components;

import com.cachenews.data.gemini.GeminiService;
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
public final class AiAssistantViewModel_Factory implements Factory<AiAssistantViewModel> {
  private final Provider<GeminiService> geminiServiceProvider;

  public AiAssistantViewModel_Factory(Provider<GeminiService> geminiServiceProvider) {
    this.geminiServiceProvider = geminiServiceProvider;
  }

  @Override
  public AiAssistantViewModel get() {
    return newInstance(geminiServiceProvider.get());
  }

  public static AiAssistantViewModel_Factory create(Provider<GeminiService> geminiServiceProvider) {
    return new AiAssistantViewModel_Factory(geminiServiceProvider);
  }

  public static AiAssistantViewModel newInstance(GeminiService geminiService) {
    return new AiAssistantViewModel(geminiService);
  }
}
