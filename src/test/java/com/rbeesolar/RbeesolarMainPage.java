package com.rbeesolar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RbeesolarMainPage {
    private WebDriver webDriver;
    private WebDriverWait wait;

    @FindBy(name = "j_username")
    WebElement loginField;

    @FindBy(name = "j_password")
    WebElement passwordField;

    @FindBy(className = "classBtn")
    WebElement loginButton;

    public RbeesolarMainPage(WebDriver driver) {
        webDriver = driver;
        wait = new WebDriverWait(webDriver, 30);

        PageFactory.initElements(webDriver, this);
    }

    public void loginToPageAsProducer(String producerLogin, String producerPassword) {
        webDriver.get("https://work.pvmeter.com?locale=en_GB");
        loginField.sendKeys(producerLogin);
        passwordField.sendKeys(producerPassword);
        loginButton.click();
    }
}
