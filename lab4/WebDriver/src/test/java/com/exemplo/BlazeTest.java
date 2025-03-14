package com.exemplo;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class BlazeTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @BeforeEach
  public void setUp() {
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @AfterEach
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void blaze() {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(550, 691));
    driver.findElement(By.cssSelector(".btn-primary")).click();
    driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
    driver.findElement(By.id("inputName")).click();
    driver.findElement(By.id("inputName")).sendKeys("miguel");
    driver.findElement(By.cssSelector(".control-group:nth-child(3) > .controls")).click();
    driver.findElement(By.id("address")).click();
    driver.findElement(By.id("address")).sendKeys("rua relva");
    driver.findElement(By.id("city")).click();
    driver.findElement(By.id("city")).sendKeys("das");
    driver.findElement(By.id("state")).click();
    driver.findElement(By.id("state")).sendKeys("asd");
    driver.findElement(By.id("zipCode")).click();
    driver.findElement(By.id("zipCode")).sendKeys("1234");
    driver.findElement(By.cssSelector(".control-group:nth-child(8) > .control-label")).click();
    driver.findElement(By.id("creditCardNumber")).sendKeys("123413254");
    driver.findElement(By.cssSelector(".btn-primary")).click();
  }
}
