package com.github.jaubuchon.seleniumutilities.weblist;

import com.github.jaubuchon.seleniumutilities.utility.iterable.IPredicate;


public class StatisticPredicate implements IPredicate<Statistic> {

  private String _firstName;
  private String _lastName;

  public StatisticPredicate(String firstName_, String lastName_) {
    this._firstName = firstName_;
    this._lastName = lastName_;
  }

  @Override
  public boolean test(Statistic statistic_) {
    return statistic_.getFirstName().equals(this._firstName)
        && statistic_.getLastName().equals(this._lastName);
  }

}
