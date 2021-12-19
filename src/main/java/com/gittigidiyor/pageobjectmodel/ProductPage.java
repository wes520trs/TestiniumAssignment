package com.gittigidiyor.pageobjectmodel;

import com.gittigidiyor.common.TestUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    WebDriver driver;
    String configFile = "config-qa.properties";
    TestUtility utility;

    @FindBy(id = "sp-subTitle")
    WebElement urunIsmi;
    @FindBy(id = "sp-price-lowPrice")
    WebElement fiyat;
    @FindBy(id = "add-to-basket")
    WebElement sepeteEkleTusu;
    @FindBy(xpath = "//span[text()='Kapat']")
    WebElement cookieClose;
    @FindBy(css = ".basket-title")
    WebElement basketIcon;


    public ProductPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
        utility=new TestUtility(driver);
    }

    public String productInfo(){
        utility.waitForElementPresent(urunIsmi);
        utility.waitForElementPresent(fiyat);
        String urunBilgisi=urunIsmi.getText()+"\n"+fiyat.getText();
        return urunBilgisi;
    }

    public void sepeteEkle(){
        utility.waitForElementPresent(cookieClose);
        cookieClose.click();
        utility.waitForElementPresent(sepeteEkleTusu);
        sepeteEkleTusu.click();
        utility.sleep(3);
        utility.waitForElementPresent(basketIcon);
        basketIcon.click();
    }


}
