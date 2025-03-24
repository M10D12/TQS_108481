package test.java.jsonplaceholder.jsonplaceholder;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class JsonPlaceholderTest {
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    void shouldReturn200ForAllTodos() {
        given()
            .when()
            .get(BASE_URL + "/todos")
            .then()
            .statusCode(200);
    }

    @Test
    void shouldReturnTodoWithTitle() {
        given()
        .when()
            .get("https://jsonplaceholder.typicode.com/todos/4")
        .then()
            .statusCode(200)
            .body("title", equalTo("et porro tempora"));
    }


    @Test
    void shouldContainIds198And199() {
        given().
        when().
            get(BASE_URL + "/todos").
        then().
            statusCode(200).
            body("id", hasItems(198, 199));
    }

    @Test
    void shouldRespondInLessThan2Seconds() {
        given().
        when().
            get(BASE_URL + "/todos").
        then().
            time(lessThan(2000L));
    }

}
