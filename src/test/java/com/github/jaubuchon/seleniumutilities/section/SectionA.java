package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

public class SectionA extends ApplicationSection {

  @ProvideContextTo(SectionB.class)
  public SectionContext getSectionCContext() {
    return new FakeContext();
  }
}
