package com.rbeesolar.tests;

import com.rbeesolar.pages.BasePage;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class RbeesolarTest {
    WebDriver driver;
    BasePage webSite;
    WebDriverWait wait;

    private String installerLogin = "demo";
    private String installerPassword = "demo69550";
    private String producerLogin = "maurice@rbee.fr";
    private String producerPassword = "123456";
    private String wrongLogin = "11111";
    private String wrongPassword = "55555";

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/rtsymbal/Documents/Development/chromedriver.exe");
        driver = new ChromeDriver();
        webSite = new BasePage(driver);
        wait = new WebDriverWait(driver, 30, 500);
        driver.get("https://work.pvmeter.com?locale=en_GB");
    }
    @Test
    public void correctDataLoginTest() {
        webSite.mainPage().loginToPage(producerLogin, producerPassword);
        //Verifying login successful by checking application page title
        wait.until(ExpectedConditions.titleContains("- Photovoltaic Monitoring"));
    }

    @Test
    public void incorrectDataLoginTest() {
        webSite.mainPage().loginToPage(wrongLogin, wrongPassword);
        //error msn should appeared
        wait.until(textToBePresentInElementLocated(By.id("error"), "Incorrect login or password"));
    }

    @Test
    public void incorrectImageCodeTest() {
        webSite.mainPage().forgotPassword.click();
        webSite.mainPage().forgotPassLoginField.sendKeys(producerLogin);
        webSite.mainPage().imageCode.sendKeys(wrongPassword);
        webSite.mainPage().loginButton.click();
        //error message should appears
        wait.until(textToBePresentInElementLocated(By.id("error"), "You have input an incorrect image code!"));
    }

    @Test
    public void userSearchTest() {
        webSite.mainPage().loginToPage(installerLogin, installerPassword);
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".gwt-TextBox:nth-child(1)")).sendKeys("Lagier");
        driver.findElement(By.xpath("//button[text()='Chercher']")).click();
        //make sure that more founded element contains "LAGIER"
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("GPIT3C4CHS"), "LAGIER"));
    }

    @Test
    public void customerEtitTest() {
        webSite.mainPage().loginToPage(installerLogin, installerPassword);
        wait.until(visibilityOfElementLocated(By.cssSelector(".GPIT3C4CF-:nth-child(1)"))).click();
        //make sure that title is changed after "edit" link click
        wait.until(textToBePresentInElementLocated(By.className("GPIT3C4CCV"), "une installation"));
        //make sure that "Lagier" data loaded
        wait.until(textToBePresentInElementLocated(By.linkText("210225531"), "210225531"));
    }

    @Test
    public void languageChangeTest() {

        webSite.mainPage().loginToPage(producerLogin, producerPassword);

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.id("gwt-debug-LanguageChooserWidgetMainLayout")).click();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//*[@class='gwt-Label' and contains(text(),'French')]")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        //make sure that language is changed by checking a title on the page
        Assert.assertThat(driver.findElement(By.className("GPIT3C4CD5")).getText(), containsString("Ma production"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
