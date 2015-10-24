package com.github.jaubuchon.seleniumutilities.section;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SeleniumClientManager {

  private final Logger _logger = Logger.getLogger(SeleniumClientManager.class);
  
  public static final String MAIN_CLIENT_KEY = "main";

  @Autowired
  ApplicationContext _applicationContext;

  Map<String, SeleniumClient> _clients = new HashMap<String, SeleniumClient>();

  /**
   * Get the main {@link SeleniumClient}.
   */
  public SeleniumClient get() {

    return this._clients.get(SeleniumClientManager.MAIN_CLIENT_KEY);
  }

  /**
   * Get a specific {@link SeleniumClient}.
   */
  public SeleniumClient get(String clientKey_) {

    SeleniumClient client = this._clients.get(clientKey_);

    if (client == null) {
      this._logger.info("Instanciate a new SeleniumClient associated to the key: " + clientKey_);
      client = this._applicationContext.getBean(SeleniumClient.class);
      this._clients.put(clientKey_, client);
    }

    return client;
  }
  
  /**
   * Get the available clients.
   */
  public Collection<SeleniumClient> getAvailableClients() {
    return this._clients.values();
  }

  /**
   * Release all the {@link SeleniumClient}. The clients must be release between each test scenarios.
   */
  public void releaseAllClients() {
    
    for(SeleniumClient client : this._clients.values()){
      client.dispose();
    }
    
    this._clients = new HashMap<String, SeleniumClient>();
  }
}
