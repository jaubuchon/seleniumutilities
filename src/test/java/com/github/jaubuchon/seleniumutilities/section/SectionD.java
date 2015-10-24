package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

@RetrieveContextFrom(SectionB.class)
public class SectionD extends ApplicationSection {

  private FakeContext _sameContextProvidedToEandF = new FakeContext();

  @ProvideContextTo(SectionE.class)
  public SectionContext getSectionEContext() {
    return _sameContextProvidedToEandF;
  }

  @ProvideContextTo(SectionF.class)
  public SectionContext getSectionFContext() {
    return _sameContextProvidedToEandF;
  }
}
