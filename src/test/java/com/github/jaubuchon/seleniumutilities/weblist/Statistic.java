package com.github.jaubuchon.seleniumutilities.weblist;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Statistic {

  private WebElement _webElement;

  public Statistic(WebElement webElement_) {
    this._webElement = webElement_;
  }

  /**
   * Get the first name of the player.
   */
  public String getFirstName() {
    return this.getColumnValue("firstname");
  }


  /**
   * Get the last name of the player.
   */
  public String getLastName() {
    return this.getColumnValue("lastname");
  }

  /**
   * Get the number of game played by the player.
   */
  public int getGamePlayed() {
    return this.getIntegerColumnValue("gameplayed");
  }

  /**
   * Get the number of win of the player.
   */
  public int getNumberOfWin() {
    return this.getIntegerColumnValue("win");
  }

  /**
   * Get the number of lost of the player.
   */
  public int getNumberOLost() {
    return this.getIntegerColumnValue("lost");
  }

  private int getIntegerColumnValue(String columnId_) {

    String stringValue = this.getColumnValue(columnId_);
    return Integer.parseInt(stringValue);
  }

  private String getColumnValue(String columnId_) {
    String xpath = String.format(".//td[@data-column='%s']", columnId_);

    return this._webElement.findElement(By.xpath(xpath)).getText();
  }
}
