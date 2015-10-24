package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.ContextRetriever;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFromCustomProvider;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFromDefaultProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ContextRetrieverStrategy {

  @Autowired
  ApplicationContext _applicationContext;

  /**
   * The good {@link ContextRetriever} is chosen depending on the section to be retrieved.
   */
  public <T extends ApplicationSection> ContextRetriever get(Class<T> sectionToBeRetrievedClass_, SectionProvider sectionProviderToUse_) {

    ContextRetriever contextRetriever = null;

    if (RetrieveContextFromCustomProvider.canRetrieve(sectionToBeRetrievedClass_)) {
      contextRetriever = this._applicationContext.getBean(RetrieveContextFromCustomProvider.class);
    } else if (RetrieveContextFromDefaultProvider.canRetrieve(sectionToBeRetrievedClass_)) {
      contextRetriever = this._applicationContext.getBean(RetrieveContextFromDefaultProvider.class);
    } else {
      throw new IllegalArgumentException(
          "There is no Context Retriever able to resolve the context of "
              + sectionToBeRetrievedClass_);
    }
    
    contextRetriever.configureSectionProvider(sectionProviderToUse_);

    return contextRetriever;

  }
}
