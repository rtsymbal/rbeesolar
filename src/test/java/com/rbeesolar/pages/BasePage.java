package com.rbeesolar.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    public BasePage(WebDriver webDriver) {
        driver = webDriver;
    }

    public MainPage mainPage() {return new MainPage(driver);}

    public FirstPage firstPage() {return new FirstPage();}

    public SecondPage secondPage() {return new SecondPage();}
}
