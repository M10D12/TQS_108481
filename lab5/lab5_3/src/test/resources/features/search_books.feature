Feature: Search for books in the online library

  Scenario: Search for a book by title
    Given I am on the online library homepage
    When I enter "Clean Code" in the search bar
    And I click the search button
    Then I should see a list of books with titles containing "Clean Code"

  Scenario: Search for a book by author
    Given I am on the online library homepage
    When I enter "Robert C. Martin" in the search bar
    And I click the search button
    Then I should see a list of books written by "Robert C. Martin"

  Scenario: Search for a book that does not exist
    Given I am on the online library homepage
    When I enter "Nonexistent Book XYZ" in the search bar
    And I click the search button
    Then I should see a message "No results found."

  Scenario: Search with an empty query
    Given I am on the online library homepage
    When I leave the search bar empty
    And I click the search button
    Then I should see an error message "Please enter a search term."
