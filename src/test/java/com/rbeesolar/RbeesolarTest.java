package com.rbeesolar;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;

public class RbeesolarTest {
    private WebDriver driver;
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
    }
    @Test
    public void correctDataLoginTest() {
        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.name("j_username")).sendKeys(producerLogin);
        driver.findElement(By.name("j_password")).sendKeys(producerPassword);
        driver.findElement(By.className("classBtn")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        String title = driver.getTitle();
        //Verifying login successful by checking application page title
        Assert.assertEquals("- Photovoltaic Monitoring", title);
    }

    @Test
    public void incorrectDataLoginTest() {
        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.name("j_username")).sendKeys(wrongLogin);
        driver.findElement(By.name("j_password")).sendKeys(wrongPassword);
        driver.findElement(By.cssSelector("[class = classBtn]")).click();
        //error msn should appeared
        Assert.assertThat(driver.findElement(By.id("error")).getText(), containsString("Incorrect login or password"));
    }

    @Test
    public void incorrectImageCodeTest() {
        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.linkText("Password lost")).click();
        driver.findElement(By.name("login")).sendKeys(producerLogin);
        driver.findElement(By.name("captcha_code")).sendKeys(wrongPassword);
        driver.findElement(By.cssSelector("[class = classBtn]")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        //error message should appears
        Assert.assertThat(driver.findElement(By.id("error")).getText(), containsString("You have input an incorrect image code!"));
    }

    @Test
    public void userSearchTest() {
        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.name("j_username")).sendKeys(installerLogin);
        driver.findElement(By.name("j_password")).sendKeys(installerPassword);
        driver.findElement(By.className("classBtn")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        //make sure that more then one elements are existing
        Assert.assertTrue(driver.findElements(By.className("GPIT3C4CHT")).size() !=0);

        driver.findElement(By.cssSelector(".gwt-TextBox:nth-child(1)")).sendKeys("Lagier");
        driver.findElement(By.xpath("//button[text()='Chercher']")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        //make sure that more founded element contains "LAGIER"
        Assert.assertThat(driver.findElement(By.className("GPIT3C4CHS")).getText(), containsString("LAGIER"));
    }

    @Test
    public void customerEtitTest() {
        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.name("j_username")).sendKeys(installerLogin);
        driver.findElement(By.name("j_password")).sendKeys(installerPassword);
        driver.findElement(By.className("classBtn")).click();

        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".GPIT3C4CF-:nth-child(1)")).click();
        //make sure that title is changed after "edit" link click
        Assert.assertThat(driver.findElement(By.className("GPIT3C4CCV")).getText(), containsString("une installation"));
        //make sure that "Lagier" data loaded
        Assert.assertThat(driver.findElement(By.linkText("210225531")).getText(), containsString("210225531"));
    }

    @Test
    public void languageChangeTest() {

        driver.get("https://work.pvmeter.com?locale=en_GB");
        driver.findElement(By.name("j_username")).sendKeys(producerLogin);
        driver.findElement(By.name("j_password")).sendKeys(producerPassword);
        driver.findElement(By.cssSelector("[class = classBtn]")).click();

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
