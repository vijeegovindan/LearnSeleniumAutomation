package com.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LearnSelenium {
    private WebDriver driver;
    private String baseURL;
    private JavascriptExecutor jse;
    List<WebElement> radioGroup;
    List<WebElement> options;

    @BeforeTest
    public void setUp() throws Exception{
        System.setProperty( "webdriver.chrome.driver", "/Users/<username>/Downloads/chrome/chromedriver" );
        driver = new ChromeDriver();
    }

    @Test
    public void testGoogle() throws Exception{
        ChromeOptions options = new ChromeOptions();
        options.addArguments( "--start--maximized" );
        driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
        driver.get("https://www.google.com/");
        WebElement element = driver.findElement( By.id( "lst-ib" ) );
        element.clear();
        element.sendKeys( "Selenium" );
        element.submit();
        WebDriverWait wait = new WebDriverWait( driver, 10 );
        wait.until( ExpectedConditions.presenceOfElementLocated(By.id( "resultStats" )));
    }

    @Test
    public void testSeleniumHQ() throws Exception{
        driver.get("http://www.seleniumhq.org");
        driver.findElement( By.linkText( "Documentation" ) ).click();
        String expectedTitle = driver.getTitle();
        Assert.assertEquals( "Selenium Documentation — Selenium Documentation", expectedTitle);
    }

    @Test
    public void testW3SchoolsCheckBox() throws Exception{
        driver.get("http://www.w3schools.com/html/html_form_input_types.asp");
        WebElement checkbox1 =  driver.findElement(By.name( "vehicle1" ));
        boolean isSelected = checkbox1.isSelected();
        Assert.assertFalse( isSelected );
    }

    @Test
    public void switchWindowHandles() throws Exception{
        driver.get("https://google.com");
        String windowGoogle = driver.getWindowHandle();
        System.out.println("This is Google Window  " + windowGoogle );
        jse = (JavascriptExecutor)driver;
        jse.executeScript("window.open('http://www.bing.com/');");
        String windowBing = driver.getWindowHandle();
        System.out.println("Window Bing:" + windowBing);
        System.out.println("Number of window handles" + driver.getWindowHandles().size());
        driver.switchTo().window(windowGoogle);
    }

    @Test
    public void testW3SchoolsRadio() throws Exception{
        driver.get( "http://www.w3schools.com/html/tryit.asp?filename=tryhtml_input_radio" );
        driver.switchTo().frame( "iframeResult" );
        radioGroup = driver.findElements( By.name("gender"));
        System.out.println("Size: " + radioGroup.size());
        for(WebElement radio: radioGroup){
            System.out.println(radio.getAttribute( "value" ));
            if(radio.isSelected()){
                System.out.println("Selected Radio Buttons:" + radio.getAttribute( "value" ));
            }
        }
        driver.switchTo().defaultContent();
    }

    @Test
    public void screenshot() throws Exception{
        driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
        driver.get("https://www.linkedin.com/");
        driver.findElement( By.id( "login-email" )).sendKeys("youremail@gmail.com");
        driver.findElement( By.id("login-password")).sendKeys( "#####" );
        driver.findElement( By.id("login-submit") ).click();
        Assert.assertNotEquals(driver.getCurrentUrl(), "https://www.linkedin.com/feed/");

    }

    @Test
    public void testW3schoolSelect() throws Exception{
        driver.get("http://www.w3schools.com/tags/tryit.asp?filename=tryhtml_select");
        driver.switchTo().frame( "iframeResult" );
        Select dropDownList = new Select( driver.findElement( By.cssSelector( "select" ) ) );
        System.out.println(dropDownList.getFirstSelectedOption().getText());

        options = dropDownList.getOptions();
        System.out.println(options.size());

        for(WebElement option: options){
            System.out.println(option.getText());
        }
        driver.switchTo().defaultContent();
    }

    @Test
    public void testXpath() throws Exception{
        driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
        driver.get("http://www.w3schools.com/xml/xpath_intro.asp");
        WebElement link = driver.findElement( By.xpath( "//*[@id='main']/h1"  ) );
        System.out.println(link.getText());

        driver.manage().timeouts().implicitlyWait( 30, TimeUnit.SECONDS );
        WebElement linkNext = driver.findElement( By.linkText( "Next ❯" ) );
        linkNext.click();
        Assert.assertTrue( driver.getCurrentUrl().equals( "https://www.w3schools.com/xml/xpath_nodes.asp" ) );
    }

    @AfterTest
    public void tearDown() throws Exception{
        driver.quit();
    }
}
