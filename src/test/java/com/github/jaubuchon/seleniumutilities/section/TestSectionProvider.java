package com.github.jaubuchon.seleniumutilities.section;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jaubuchon.seleniumutilities.section.SectionProvider;

@ContextConfiguration("classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSectionProvider {

  @Autowired
  SectionProvider _sectionProvider;

  /**
   * Setup the the test scenario.
   */
  @Before
  public void setUp() throws Exception {

    SectionA rootProvider = new SectionA();
    rootProvider.setSectionContext(new FakeContext());

    this._sectionProvider.registerRootProvider(rootProvider);
  }

  @Test
  public void retrieveSectionD_Allways_SectionDRetrieved() {

    SectionD sectionD = this._sectionProvider.retrieveSection(SectionD.class);

    Assert.assertNotNull(sectionD);
    Assert.assertTrue(sectionD.getSectionContext().isValid());
  }

  @Test
  public void retrieveSectionD_DContextStillValid_SectionDReturnedFromCache() {

    SectionD sectionD1 = _sectionProvider.retrieveSection(SectionD.class);

    SectionD sectionD2 = _sectionProvider.retrieveSection(SectionD.class);

    Assert.assertTrue(sectionD1 == sectionD2);
  }

  @Test
  public void retrieveSectionD_DContextNotValidAnymore_SectionDRebuilt() {

    SectionD sectionD1 = _sectionProvider.retrieveSection(SectionD.class);

    ((FakeContext) sectionD1.getSectionContext()).invalidate();

    SectionD sectionD2 = _sectionProvider.retrieveSection(SectionD.class);

    Assert.assertFalse(sectionD1 == sectionD2);
  }

  @Test
  public void retrieveRoot_Allways_RootReturned() {

    SectionA sectionA = _sectionProvider.retrieveSection(SectionA.class);

    Assert.assertNotNull(sectionA);
  }

  @Test
  public void retrieveSectionEandF_Allways_SectionDIsRetrievedFromCache() {

    SectionE sectionE = _sectionProvider.retrieveSection(SectionE.class);

    SectionF sectionF = _sectionProvider.retrieveSection(SectionF.class);

    // SectionD is providing the same context instance to SectionE and SectionF.
    // So if the instance of SectionE.context is the same of the instance of
    // SectionF.context, then it means that the context is coming from the same instance
    // of SectionD. In other words it confirms that SectionD is retrieved from cache.
    Assert.assertTrue(sectionE.getSectionContext() == sectionF.getSectionContext());
  }
}
