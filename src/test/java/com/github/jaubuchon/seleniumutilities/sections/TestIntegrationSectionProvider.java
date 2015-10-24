package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.IntegrationTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestIntegrationSectionProvider extends IntegrationTest {

  @Test
  public void retrieveSection_SectionStillInCacheAndContextIsStillValid_ReturnCachedSection() {

    // Given: The StatisticTab was already retrieved
    StatisticTab statisticTab = this._sectionUtility.retrieve(StatisticTab.class);

    // When: I retrieve the StatisticTab a second time
    StatisticTab statisticTabCached = this._sectionUtility.retrieve(StatisticTab.class);

    // Then: The StatisticTab is retrieved from the cache
    Assert.assertTrue(statisticTab == statisticTabCached);
  }

  @Test
  public void retrieveSection_SectionStillInCacheButContextNoMoreValid_ReturnCachedSection() {

    // Given: The StatisticTab was already retrieved
    StatisticTab statisticTab = this._sectionUtility.retrieve(StatisticTab.class);

    // But the context has been changed since (the AddPlayerOrMatchTab has been selected).
    this._sectionUtility.retrieve(AddPlayerOrMatchTab.class);

    // When: I retrieve the StatisticTab a second time
    StatisticTab statisticTabNewInstance = this._sectionUtility.retrieve(StatisticTab.class);

    // Then: The cached StatisticTab has now an invalid context, so the {@link StatisticTab} is
    // resolved again
    Assert.assertTrue(statisticTab != statisticTabNewInstance);
  }

  @Test
  public void retrieveSection_TwoSectionsHaveACommonParent_ParentSectionIsCached() {

    // Given: Given: The StatisticTab was already retrieved
    this._sectionUtility.retrieve(StatisticTab.class);

    AuthenticatedApplication authenticatedApplication1 =
        this._sectionUtility.retrieve(AuthenticatedApplication.class);

    // When: I retrieve the AddPlayerOrMatchTab (that is sharing the same parent with StatisticTab:
    // the AuthenticatedApplication).
    this._sectionUtility.retrieve(AddPlayerOrMatchTab.class);

    AuthenticatedApplication authenticatedApplication2 =
        this._sectionUtility.retrieve(AuthenticatedApplication.class);

    // Then: The parent was cached and reuse to provide context to the two children (StatisticTab
    // and AddPlayerOrMatchTab).
    Assert.assertTrue(authenticatedApplication1 == authenticatedApplication2);
  }
}
