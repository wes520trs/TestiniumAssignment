package com.gittigidiyor.pageobjectmodel;

import com.gittigidiyor.common.TestUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    WebDriver driver;
    TestUtility utility;

    @FindBy(name = "k")
    WebElement textField;
    @FindBy(xpath = "//span[text()='BUL']")
    WebElement searchButton;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        utility = new TestUtility(driver);
    }

    public void searching() {
        utility.waitForElementPresent(textField);
        String keyWord = utility.readConfigProperties("config-qa.properties", "keyWord");
        textField.sendKeys(keyWord);
        utility.waitForElementPresent(searchButton);
        searchButton.click();
    }
}
