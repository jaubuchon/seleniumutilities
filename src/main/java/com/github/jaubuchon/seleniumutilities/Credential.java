package com.github.jaubuchon.seleniumutilities;

public class Credential {
  private String _username;
  private String _password;

  public Credential(String username_, String password_) {
    this._username = username_;
    this._password = password_;
  }

  public String getUsername() {
    return _username;
  }

  public String getPassword() {
    return _password;
  }
}
