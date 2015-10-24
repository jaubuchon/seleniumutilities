package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

@RetrieveContextFrom(SectionA.class)
public class SectionB extends ApplicationSection {

  @ProvideContextTo(SectionC.class)
  public SectionContext getSectionCContext() {
    return new FakeContext();
  }

  @ProvideContextTo(SectionD.class)
  public SectionContext getSectionDContext() {
    return new FakeContext();
  }
}
