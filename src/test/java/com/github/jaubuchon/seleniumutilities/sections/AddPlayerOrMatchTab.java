package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContext;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;

@RetrieveContextFrom(AuthenticatedApplication.class)
public class AddPlayerOrMatchTab extends ApplicationSection {
  
  
  @RetrieveContext(from = AddPlayerOrMatchTab.class, id="add-player-section")
  public class AddPlayerSection extends ApplicationSection{

  }

}
