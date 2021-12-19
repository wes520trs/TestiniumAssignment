package com.gittigidiyor;

import com.gittigidiyor.common.TestBase;
import com.gittigidiyor.common.TestUtility;
import com.gittigidiyor.pageobjectmodel.BasketPage;
import com.gittigidiyor.pageobjectmodel.HomePage;
import com.gittigidiyor.pageobjectmodel.ProductPage;
import com.gittigidiyor.pageobjectmodel.SearchResultPage;
import org.junit.*;

public class TestRunner extends TestBase {

    static TestUtility utility;
    static HomePage homePage;
    static SearchResultPage searchResultPage;
    static ProductPage productPage;
    static BasketPage basketPage;

    @BeforeClass
    public static void setUp() {
        initialization("homePage_url"); //www.gittigidiyor.com sitesi açılır.
        utility = new TestUtility(driver);
        homePage = new HomePage(driver);
        searchResultPage = new SearchResultPage(driver);
        productPage = new ProductPage(driver);
        basketPage = new BasketPage(driver);
    }

    @Test
    public void Test01_SearchProduct() {
        String firstPage_url = driver.getCurrentUrl();
        homePage.searching(); //Arama kutucuğuna bilgisayar kelimesi girilir.
        String secondPage_url = driver.getCurrentUrl(); //Arama sonuçları sayfasından 2.sayfa açılır.
        Assert.assertTrue(!firstPage_url.equals(secondPage_url)); //2.sayfanın açıldığı kontrol edilir.
    }

    @Test
    public void Test02_ProductAction() {
        searchResultPage.clickAProduct(); //Sonuca göre sergilenen ürünlerden rastgele bir ürün seçilir.
        String infoFromProductPage = (productPage.productInfo()).toLowerCase();
        utility.writeToFile("bilgisayar", infoFromProductPage); //Seçilen ürünün ürün bilgisi ve tutar bilgisi txt dosyasına yazılır.
        productPage.sepeteEkle(); //Seçilen ürün sepete eklenir.
        String infoFromBasketPage = (basketPage.getInfo()).toLowerCase();
        System.out.println("info from basket page: " + infoFromBasketPage);
        basketPage.changeQty(2);
        Assert.assertEquals(basketPage.qtyInfo(), 2); //Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.
        Assert.assertEquals(infoFromBasketPage, infoFromProductPage); //Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.
        basketPage.deleteProduct();
        Assert.assertTrue(basketPage.isBasketEmpity()); //Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.
    }

}
