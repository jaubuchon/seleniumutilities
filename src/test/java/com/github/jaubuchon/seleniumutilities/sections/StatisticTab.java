package com.github.jaubuchon.seleniumutilities.sections;

import com.github.jaubuchon.seleniumutilities.list.SeleniumAgentIterable;
import com.github.jaubuchon.seleniumutilities.section.ApplicationSection;
import com.github.jaubuchon.seleniumutilities.section.context.RetrieveContextFrom;
import com.github.jaubuchon.seleniumutilities.weblist.StatisticsWebList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RetrieveContextFrom(AuthenticatedApplication.class)
public class StatisticTab extends ApplicationSection {

  /**
   * Get a {@link SeleniumAgentIterable} representation of the the stats table.
   */
  public StatisticsWebList getStatsTable() {

    WebElement statsTable = this.find(By.id("stats-table"));

    return new StatisticsWebList(statsTable);
  }

}
