package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProvideContextTo {
  
  /**
   * This is the {@link ApplicationSection} to which the context is giving to.
   */
  Class<? extends ApplicationSection> value();
}
