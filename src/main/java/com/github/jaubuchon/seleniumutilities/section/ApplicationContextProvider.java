package com.github.jaubuchon.seleniumutilities.section;

import com.github.jaubuchon.seleniumutilities.section.context.ProvideContextTo;
import com.github.jaubuchon.seleniumutilities.section.context.SectionContext;
import com.github.jaubuchon.seleniumutilities.section.context.WebDriverSectionContext;

import org.apache.log4j.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

/**
 * This is the section that is providing the ApplicationContext 
 * (a browser that is pointing on the application URL).
 */
public class ApplicationContextProvider extends ApplicationSection {

  private final Logger _logger = Logger.getLogger(ApplicationContextProvider.class);
  
  private String _applicationUrl;
  private IApplicationLoadedEvaluator _applicationLoadEvaluator;

  public void setApplicationUrl(String applicationUrl_) {
    this._applicationUrl = applicationUrl_;
  }

  public void setApplicationLoadEvaluator(IApplicationLoadedEvaluator evaluator_) {
    this._applicationLoadEvaluator = evaluator_;
  }

  /**
   * Provide the context to the {@link Application}. Ensure the browser is pointing on the
   * application url.
   */
  @ProvideContextTo(Application.class)
  public SectionContext getApplicationContext() {

    if (!this._applicationLoadEvaluator.isApplicationLoaded(this.getSectionContext())) {
      this.navigateToLoginPage();
    }

    return new ApplicationPageContext(this.getSectionContext().getSearchContext());
  }

  private WebDriverSectionContext getWebDriverSectionContext() {

    // We assume that the SectionContext of this Section is a WebDriverSectionContext
    return (WebDriverSectionContext) this.getSectionContext();
  }

  private void navigateToLoginPage() {
    WebDriver webDriver = this.getWebDriverSectionContext().getWebDriver();
    
    this._logger.info("start navigating to: "+ this._applicationUrl);
    webDriver.navigate().to(this._applicationUrl);
    this._logger.info("navigate done");
  }

  public class ApplicationPageContext extends SectionContext {

    public ApplicationPageContext(SearchContext searchContext_) {
      super(searchContext_);
    }

    @Override
    public boolean isValid() {
      return this.isApplicationLoaded();
    }

    @Override
    public boolean isVisible() {
      return this.isApplicationLoaded();
    }

    private boolean isApplicationLoaded() {
      return _applicationLoadEvaluator.isApplicationLoaded(this);
    }
  }
}
