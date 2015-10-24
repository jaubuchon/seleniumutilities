package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;

public class FakeContext extends SectionContext {

  boolean _isContextValid = true;

  public FakeContext() {
    super(null);
  }

  @Override
  public boolean isValid() {
    return this._isContextValid;
  }

  @Override
  public boolean isVisible() {
    return true;
  }

  public void invalidate() {
    this._isContextValid = false;
  }
}
