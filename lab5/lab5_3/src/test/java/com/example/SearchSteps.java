package com.example;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class SearchSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    @Given("I am on the online library homepage")
    public void i_am_on_the_online_library_homepage() {
        driver.get("https://cover-bookstore.onrender.com/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-testid='book-search-input']")));
    }

    @When("I enter {string} in the search bar")
    public void i_enter_text_in_the_search_bar(String searchText) {
        WebElement searchBox = driver.findElement(By.cssSelector("input[data-testid='book-search-input']"));
        searchBox.clear();
        searchBox.sendKeys(searchText);
    }

    @When("I click the search button")
    public void i_click_the_search_button() {
        WebElement searchButton = driver.findElement(By.cssSelector("button.Navbar_searchBtn__26UF_"));
        searchButton.click();
    }

    @Then("I should see a list of books with titles containing {string}")
    public void i_should_see_a_list_of_books(String expectedTitle) {
        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div[class*='search-results'], div[class*='book-list']") 
        ));

        List<WebElement> books = resultsContainer.findElements(By.tagName("h2")); 
        boolean found = books.stream().anyMatch(book -> book.getText().contains(expectedTitle));

        Assert.assertTrue("Book not found in results", found);
    }

    @Then("I should see a list of books written by {string}")
    public void i_should_see_a_list_of_books_written_by(String authorName) {
        WebElement resultsContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("div[class*='search-results'], div[class*='book-list']") 
        ));

        List<WebElement> books = resultsContainer.findElements(By.xpath(".//p[contains(@class, 'author')]")); 
        boolean found = books.stream().anyMatch(book -> book.getText().contains(authorName));

        Assert.assertTrue("Books by author not found in results", found);
    }

    @Then("I should see a message {string}")
    public void i_should_see_a_message(String expectedMessage) {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), '" + expectedMessage + "')]") 
        ));
        Assert.assertEquals("No results message incorrect", expectedMessage, message.getText());
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String expectedMessage) {
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(), '" + expectedMessage + "')]") 
        ));
        Assert.assertEquals("Error message does not match", expectedMessage, errorMessage.getText());
    }

    @When("I leave the search bar empty")
    public void i_leave_the_search_bar_empty() {
        WebElement searchBox = driver.findElement(By.cssSelector("input[data-testid='book-search-input']"));
        searchBox.clear();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
