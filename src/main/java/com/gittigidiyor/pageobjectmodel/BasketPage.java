package com.gittigidiyor.pageobjectmodel;

import com.gittigidiyor.common.TestUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BasketPage {

    WebDriver driver;
    TestUtility utility;

    @FindBy(css = ".text-box h2")
    WebElement nameOfProduct;
    @FindBy(css = ".total-price")
    WebElement priceOfProdcut;
    @FindBy(css = "select.amount")
    WebElement quantity;
    @FindBy(css = ".gg-icon.gg-icon-bin-medium")
    WebElement deleteIcon;
    @FindBy(xpath = "//*[text()=\"Sepetinizde ürün bulunmamaktadır.\"]")
    WebElement emptyBasketMessage;


    public BasketPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        utility = new TestUtility(driver);
    }

    public String getInfo() {
        utility.waitForElementPresent(nameOfProduct);
        utility.waitForElementPresent(priceOfProdcut);
        String productInfo = nameOfProduct.getText() + "\n" + priceOfProdcut.getText();
        return productInfo;
    }

    public void changeQty(int i) {
        utility.waitForElementPresent(quantity);
        quantity.click();
        Select select = new Select(quantity);
        String j = Integer.toString(i);
        select.selectByValue(j);
    }

    public int qtyInfo() {
        int qty = Integer.parseInt(quantity.getAttribute("value"));
        return qty;
    }

    public void deleteProduct() {
        utility.waitForElementPresent(deleteIcon);
        deleteIcon.click();
    }

    public boolean isBasketEmpity() {
        if (emptyBasketMessage.isDisplayed()) {
            return true;
        } else
            return false;
    }

}
