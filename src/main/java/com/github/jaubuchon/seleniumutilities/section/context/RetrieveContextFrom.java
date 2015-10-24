package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retrieve the context of an {@link ApplicationSection} from a {@link ProvideContextTo} annotated method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RetrieveContextFrom {
  
  /**
   * This is the class that is exposing a method a {@link ProvideContextTo} annotated method.
   * @link ProvideContextTo} annotated method is providing the context.
   */
  Class<? extends ApplicationSection> value();
}
