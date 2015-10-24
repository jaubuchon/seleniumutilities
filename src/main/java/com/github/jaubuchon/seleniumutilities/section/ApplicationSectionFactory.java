package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationSectionFactory {

  @Autowired
  ApplicationContext _applicationContext;

  /**
   * Instantiate an {@link ApplicationSection} and inject it his context.
   */
  @SuppressWarnings({"unchecked"})
  public <T extends ApplicationSection> T build(Class<T> clazz_, SectionContext sectionContext_) {

    ApplicationSection section = this._applicationContext.getBean(clazz_);

    section.setSectionContext(sectionContext_);

    return (T) section;
  }
}
