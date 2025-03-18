package com.exemplo;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;
import com.exemplo.Library;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @ParameterType("\\d{4}-\\d{2}-\\d{2}")
    public LocalDate iso8601Date(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
    }
    


    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published.atStartOfDay());
        library.addBook(book);
    }


	@Given("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addAnotherBook(final String title, final String author, final LocalDate published) {
        Book book = new Book(title, author, published.atStartOfDay());
        library.addBook(book);
    }



    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final LocalDate from, final LocalDate to) {
        result = library.findBooks(from.atStartOfDay(), to.atTime(23, 59, 59));
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        System.out.println("Books found: " + result.size());
        for (Book book : result) {
            System.out.println("Title: " + book.getTitle() + " - Published: " + book.getPublished());
        }
        assertThat(result.size(), equalTo(booksFound));
    }


    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        System.out.println("Checking position " + position + " - Expected: " + title + " - Found: " + result.get(position - 1).getTitle());
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
    
}
