package com.rbeesolar.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver webDriver) {
        driver = webDriver;
        wait = new WebDriverWait(driver, 30);

        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "j_username")
    public WebElement loginField;

    @FindBy(name = "j_password")
    public WebElement passwordField;

    @FindBy(className = "classBtn")
    public WebElement loginButton;

    @FindBy(linkText = "Password lost")
    public WebElement forgotPassword;

    @FindBy(name = "login")
    public WebElement forgotPassLoginField;

    @FindBy(name = "captcha_code")
    public WebElement imageCode;

    public void loginToPage(String login, String password) {

        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginButton.click();
    }
}
