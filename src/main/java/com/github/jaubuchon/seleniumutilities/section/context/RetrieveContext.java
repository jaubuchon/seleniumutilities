package com.github.jaubuchon.seleniumutilities.section.context;

import com.github.jaubuchon.seleniumutilities.section.Application;
import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retrieve the context of an {@link ApplicationSection} by searching into the context of an existent {@link ApplicationSection}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RetrieveContext {

  /**
   * From which {@link ApplicationSection} to find the context.
   */
  Class<? extends ApplicationSection> from() default Application.class;
  
  /**
   * If defined, it finds by id the section.
   */
  String id() default StringUtils.EMPTY;

  /**
   * If defined, it finds by xpath the section.
   */
  String xpath() default StringUtils.EMPTY;
}
