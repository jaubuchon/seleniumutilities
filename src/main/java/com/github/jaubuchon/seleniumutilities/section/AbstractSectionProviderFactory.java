package com.github.jaubuchon.seleniumutilities.section;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * Each project needs to provides his own implementation of this class.
 */
public abstract class AbstractSectionProviderFactory {
  
  @Autowired
  protected ApplicationContext _applicationContext;
  
  /**
   * Build a new {@link SectionProvider}.
   * 
   * @param webDriver_ the {@link WebDriver} from which the sections will be retrieved.
   */
  /**
   * Build a new {@link SectionProvider}.
   * @param seleniumClient_ the {@link SeleniumClient} to which the {@link SectionProvider} is associated.
   */
  public SectionProvider build(SeleniumClient seleniumClient_) {

    SectionProvider sectionProvider = this._applicationContext.getBean(SectionProvider.class);

    sectionProvider.setSeleniumClient(seleniumClient_);
    
    this.init(sectionProvider, seleniumClient_);

    return sectionProvider;
  }

  /**
   * Initialize the {@link SectionProvider}.
   * You will need to override this method in order to configure the {@link SectionProvider}
   * for your specific project.
   * 
   * @param sectionProvider_ the {@link SectionProvider} to initialize
   * @param seleniumClient_ the {@link SeleniumClient} to which the {@link SectionProvider} is associated
   */
  protected abstract void init(SectionProvider sectionProvider_, SeleniumClient seleniumClient_);

}
