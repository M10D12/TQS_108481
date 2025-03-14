package com.exemplo;

import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CalculatorSteps {
    private int result;
    private String errorMessage;

    @Given("a calculator I just turned on")
    public void a_calculator_i_just_turned_on() {
        result = 0; 
        errorMessage = null; 
    }

    @When("I add {int} and {int}")
    public void i_add_and(int a, int b) {
        result = a + b;
    }

    @When("I substract {int} to {int}")
    public void i_substract_to(int a, int b) {
        result = a - b;
    }

    @When("I multiply {int} by {int}")
    public void i_multiply_and(int a, int b) {
        result = a * b;
    }

    @When("I perform an invalid operation {string}")
    public void i_perform_an_invalid_operation(String operation) {
        errorMessage = "Invalid operation: " + operation;
    }

    @Then("the result is {int}")
    public void the_result_is(int expected) {
        assertEquals(expected, result, "Incorrect result!");
    }

    @Then("I should see an error message")
    public void i_should_see_an_error_message() {
        assertNotNull(errorMessage, "Expected an error message, but none was given.");
    }
}
