package com.gittigidiyor.common;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestUtility {

    private int timeout = Integer.parseInt(readConfigProperties("config-qa.properties", "timeout"));
    private WebDriver driver;

    public TestUtility(WebDriver driver) {
        this.driver = driver;
    }

    public TestUtility() {
    }

    public void waitForElementPresent(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOf(element));
    }


    public void waitForElementClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public void waitForAlertPresent() {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void dismissAlert() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public void takeScreenShot(String fileName, WebDriver driver) {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File screenShotFile = screenshot.getScreenshotAs(OutputType.FILE);
        String folder = readConfigProperties("config-qa.properties",
                "imageFolder");
        DateTime date = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd-HH-mm-ss");
        String timeStamp = date.toString(formatter);
        fileName = fileName + "." + timeStamp;
        File finalFile = new File(folder + File.separator + fileName + ".png");
        try {
            FileUtils.copyFile(screenShotFile, finalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void highLightElement(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String backGroundColor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 3; i++) {
            changeElementColor("#D42D1B", element, driver);
            changeElementColor(backGroundColor, element, driver);
        }
    }

    public void changeElementColor(String color, WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;//downcasting
        js.executeScript("arguments[0].style.backgroundColor= '" + color + "'", element);
        sleep(2);
    }

    public void setElementBorder(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='4px solid red' ", element);
    }

    public void clickWithJS(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click", element);
    }

    public String getTitle(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String title = js.executeScript("return document.title").toString();
        return title;
    }

    public void scrollToElementAppears(WebElement element, WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void refresh(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("history.go(0)");
    }

    public void scrollToElement(WebElement Element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", Element);
    }

    public void scrollToTheTop(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(1000,0)");
    }

    public void scrollToTheBottom(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("scroll(0,1000)");
    }

    public String readConfigProperties(String fileName, String key) {
        Properties prop = new Properties();
        String workingDirectory = System.getProperty("user.dir");
        String value;
        try {
            prop.load(new FileInputStream(workingDirectory + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        value = prop.getProperty(key);
        System.out.println(String.format("Reading from config file: %s=%s", key, value));
        return value;
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String fileName, String content) {
        //create txt file
        try {
            File myObj = new File(fileName + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        // write to txt file
        try {
            FileWriter myWriter = new FileWriter(fileName + ".txt");
            myWriter.write(content);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
