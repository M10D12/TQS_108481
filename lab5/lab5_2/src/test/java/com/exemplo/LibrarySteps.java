package com.exemplo;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibrarySteps {
    private List<Book> library = new ArrayList<>();
    private List<Book> searchResults;

    @Given("the library contains the following books")
    public void the_library_contains_the_following_books(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            library.add(new Book(
                    row.get("title"),
                    row.get("author"),
                    LocalDate.parse(row.get("published"))
            ));
        }
    }

    @When("the customer searches for books by author {string}")
    public void the_customer_searches_for_books_by_author(String author) {
        searchResults = library.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void the_customer_searches_for_books_published_between(LocalDate startDate, LocalDate endDate) {
        searchResults = library.stream()
                .filter(book -> !book.getPublished().isBefore(startDate) && !book.getPublished().isAfter(endDate))
                .collect(Collectors.toList());
    }

    @Then("the following books should be found")
    public void the_following_books_should_be_found(DataTable expectedTable) {
        List<Map<String, String>> expectedRows = expectedTable.asMaps(String.class, String.class);
        List<Map<String, String>> actualResults = searchResults.stream().map(book -> {
            Map<String, String> map = new HashMap<>();
            map.put("title", book.getTitle());
            map.put("author", book.getAuthor());
            map.put("published", book.getPublished().toString());
            return map;
        }).collect(Collectors.toList());

        assertEquals(expectedRows, actualResults, "Book search results do not match expected values!");
    }

    @Then("no books should be found")
    public void no_books_should_be_found() {
        assertEquals(0, searchResults.size(), "Expected no books to be found!");
    }

    @ParameterType(".*")
    public LocalDate iso8601Date(String date) {
        return LocalDate.parse(date);
    }
}
