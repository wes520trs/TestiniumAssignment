package com.gittigidiyor.common;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestBase {
    public static WebDriver driver;
    public static String configFile = "config-qa.properties";
    public static String oSName = System.getProperty("os.name");

    static TestUtility utility=null;

    public static void initialization(String url_key) {
        if (driver == null) {
            boolean useHeadless = useHeadless();
            if (oSName.toLowerCase().contains("mac")) {
                System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (useHeadless) {
                    System.out.println("Operating System: " + oSName);
                    System.out.println("Headless mode initialized");
                    options.addArguments(Arrays.asList("--headless", "--disable-gpu"));
                    options.addArguments("window-size=1200,1100");
                }
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
            } else if (oSName.contains("Windows")) {
                System.setProperty("webdriver.chrome.driver", "c:\\webdriver\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                //Block site notification
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("profile.default_content_setting_values.notifications", 2);
                options.setExperimentalOption("prefs", prefs);
                if (useHeadless) {
                    System.out.println("Operating System: " + oSName);
                    System.out.println("Headless mode initialized");
                    options.addArguments(Arrays.asList("--headless", "--disable-gpu"));
                    options.addArguments("window-size=1200,1100");
                }
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
            } else {
                System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
                System.out.println("Operating System: " + oSName);
                ChromeOptions options = new ChromeOptions();
                options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                options.addArguments(Arrays.asList("--headless", "disable-gpu"));
                options.addArguments("window-size=1920,1080");
                driver = new ChromeDriver(options);
            }
        }
        utility=new TestUtility();
        driver.get(utility.readConfigProperties("config-qa.properties", url_key));
    }

    public static void closeBrowser() {
        driver.close();
        driver = null;
    }

    public static boolean useHeadless() {
        utility=new TestUtility();
        return Integer.parseInt(utility.readConfigProperties("config-qa.properties", "headless")) == 1;
    }

}
