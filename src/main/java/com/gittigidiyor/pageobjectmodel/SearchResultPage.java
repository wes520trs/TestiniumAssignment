package com.gittigidiyor.pageobjectmodel;

import com.gittigidiyor.common.TestUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchResultPage {

    WebDriver driver;
    String configFile = "config-qa.properties";
    TestUtility utility;

@FindBy(css = ".sc-1nx8ums-0.dyekHG")
    WebElement urunIkonu;


public SearchResultPage(WebDriver driver){
    this.driver=driver;
    PageFactory.initElements(driver,this);
    utility=new TestUtility(driver);
}

public void clickAProduct(){
    utility.waitForElementPresent(urunIkonu);
    utility.scrollToElement(urunIkonu);
    urunIkonu.click();
}

}
