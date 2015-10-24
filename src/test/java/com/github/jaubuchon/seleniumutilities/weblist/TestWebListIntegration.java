package com.github.jaubuchon.seleniumutilities.weblist;

import com.github.jaubuchon.seleniumutilities.IntegrationTest;
import com.github.jaubuchon.seleniumutilities.sections.StatisticTab;
import com.github.jaubuchon.seleniumutilities.utility.iterable.IPredicate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.NoSuchElementException;

@ContextConfiguration("classpath:application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestWebListIntegration extends IntegrationTest {

  @Test
  public void countNumberOfItemsInTheList() {

    // Given a WebList with seven items in it
    StatisticsWebList statsWebList = this.getStatsWebList();
    
    // When counting the number of items in the WebList 
    int actualNumberOfItemsInTheList = statsWebList.count();
    
    // Then the counted number of items is correct
    Assert.assertEquals(7, actualNumberOfItemsInTheList);
  }

  @Test
  public void getFirstItemInTheList_allways_firstItemReturned() {

    // Given a WebList
    StatisticsWebList statsWebList = this.getStatsWebList();
    
    // When getting the first item of the WebList
    Statistic firstItemInTheList = statsWebList.first();
    
    // Then the first element of the list is returned
    Assert.assertEquals("Paul", firstItemInTheList.getFirstName());
    Assert.assertEquals("Bibeault", firstItemInTheList.getLastName());
  }

  @Test
  public void getSingleItemInTheList_itemExistent_expectedItemReturned() {
    
    // Given a WebList
    StatisticsWebList statsWebList = this.getStatsWebList();

    // When getting a single item that is existing
    Statistic marcDenisStat = statsWebList.single(new StatisticPredicate("Marc", "Denis"));

    // Then the expected item is returned
    Assert.assertEquals("Marc", marcDenisStat.getFirstName());
    Assert.assertEquals("Denis", marcDenisStat.getLastName());
  }
  
  @Test
  public void getSingleItemWithPredicate_itemInexistent_noSuchElementException() {
    
    // Given a WebList with no "Inexistent Player" in it
    StatisticsWebList statsWebList = this.getStatsWebList();

    try {
      // When getting a single "Inexistent Player"
      statsWebList.single(new StatisticPredicate("Inexistent", "Player"));
      Assert.fail("A NoSuchElementException was expected");
    } catch (NoSuchElementException exception) {
      
      // Then a NoSuchElementException is thrown because no player
      // named "Inexistent Player" was in the list
      Assert.assertTrue(true);
    } catch (Exception exception) {
      
      Assert.fail("The wrong exception was thrown");
    }
  }
  
  @Test
  public void getSingleItem_moreThanOneItemAvailable_illegalStateException() {
    
    // Given a WebList with more than one item
    StatisticsWebList statsWebList = this.getStatsWebList();

    try {
      
      // When asking for a single item
      statsWebList.single();
      Assert.fail("A NoSuchElementException was expected");
    } catch (IllegalStateException exception) {
      
      // Then an IllegalStateException is thrown because
      // more than one item was available in the list
      Assert.assertTrue(true);
    } catch (Exception exception) {
      
      Assert.fail("The wrong exception was thrown");
    }
  }
  
  @Test
  public void isAny_itemExist_true() {
    
    // Given a WebList with "Gerry McNeil" in it
    StatisticsWebList statsWebList = this.getStatsWebList();
    
    // When verifying if "Gerry McNeil" is in the list
    boolean isAny = statsWebList.any(new StatisticPredicate("Gerry", "McNeil"));
    
    // Then it should return yes
    Assert.assertTrue(isAny);
  }
  
  @Test
  public void isAny_itemNotExistent_false() {
    
    // Given a WebList with no "Wrong Player" in it
    StatisticsWebList statsWebList = this.getStatsWebList();
    
    // When verifying if "Wrong Player" is in the list
    boolean isAny = statsWebList.any(new StatisticPredicate("Wrong", "Player"));
    
    // Then it should return no
    Assert.assertFalse(isAny);
  }
  
  @Test
  public void where_withACondition_ItemsSatisfyingTheConditionReturned() {
    
    // Given a WebList containing three players who played more than 100 games
    StatisticsWebList statsWebList = this.getStatsWebList();
    
    // When searching for player who played more than 100 games
    List<Statistic> result = statsWebList.where(new IPredicate<Statistic>() {
      
      @Override
      public boolean test(Statistic statistic_) {
        return statistic_.getGamePlayed() >= 100;
      }
    });
    
    // Then the tree players who have played more than 100 games are returned
    Assert.assertEquals(3, result.size());
    
    for (Statistic statistic: result) {
      Assert.assertTrue(statistic.getGamePlayed() >= 100);
    }
  }

 

  private StatisticsWebList getStatsWebList() {

    StatisticTab statisticTab = this._sectionUtility.retrieve(StatisticTab.class);

    return statisticTab.getStatsTable();
  }

}
