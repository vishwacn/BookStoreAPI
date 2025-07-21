package tests;

import static constants.EndPoints.*;
import static io.restassured.RestAssured.given;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import base.BaseTest;
import config.ConfigManager;
import io.restassured.response.Response;


public class BooksSerTest extends BaseTest {

    public Response response;
    public static String isbnNumber;
    public static String bookTitle;

    private String getAuthHeader() {
        return "Bearer " + AccountsTest.token;
    }

    private void logAndAssertResponse(String title, String responseBody, boolean condition, String passMessage) {
        test.log(Status.INFO, title + ": " + responseBody);
        System.out.println(title + ": " + responseBody);
        Assert.assertTrue(condition, passMessage);
        test.log(Status.PASS, passMessage);
    }

    @Test(priority = 1, dependsOnMethods = {
            "tests.AccountsTest.testCreateUser",
            "tests.AccountsTest.testGenerateToken" })
    public void createBookInCollection() {
        test = report.createTest("Create Book in Collection");

        BookCollectionRequest request = new BookCollectionRequest(
                AccountsTest.id,
                List.of(new Book(ConfigManager.get("firstIsbn")))
        );

        response = given()
                .header("Authorization", getAuthHeader())
                .contentType("application/json")
                .body(request)
                .when()
                .post(ADD_BOOKS)
                .then()
                .statusCode(201)
                .extract().response();

        isbnNumber = response.jsonPath().getString("books[0].isbn");
        logAndAssertResponse("Create Book Response", response.getBody().asString(), isbnNumber != null,
                "Book created successfully with ISBN: " + isbnNumber);
    }

    @Test(priority = 2, dependsOnMethods = "createBookInCollection")
    public void testGetAllBooks() {
        test = report.createTest("Get All Books");

        response = given()
                .when()
                .get(GET_BOOKS)
                .then()
                .statusCode(200)
                .extract().response();

        bookTitle = response.jsonPath().getString("books[0].title");
        logAndAssertResponse("All Books", response.getBody().asString(), bookTitle != null,
                "Book title retrieved: " + bookTitle);
    }

    @Test(priority = 3, dependsOnMethods = "createBookInCollection")
    public void updateBookInCollection() {
        test = report.createTest("Update Book - Not Supported");

        BookActionRequest request = new BookActionRequest(AccountsTest.id, isbnNumber);

        response = given()
                .header("Authorization", getAuthHeader())
                .contentType("application/json")
                .body(request)
                .pathParam("isbn", isbnNumber)
                .when()
                .put(UPDATE_BOOKS + "{isbn}")
                .then()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400);
        logAndAssertResponse("Update Book Response", response.getBody().asString(), true,
                "Book update not supported (400 returned as expected).");
    }

    @Test(priority = 4, dependsOnMethods = "createBookInCollection")
    public void deleteBookFromCollection() {
        test = report.createTest("Delete Book");

        BookActionRequest request = new BookActionRequest(AccountsTest.id, isbnNumber);

        response = given()
                .header("Authorization", getAuthHeader())
                .contentType("application/json")
                .body(request)
                .when()
                .delete(DELETE_BOOK)
                .then()
                .statusCode(204)
                .extract().response();

        String responseBody = response.getBody().asString();
        boolean isEmpty = responseBody == null || responseBody.trim().isEmpty();
        logAndAssertResponse("Delete Book", responseBody, isEmpty, "Book deleted successfully. Empty response confirmed.");
    }

    @Test(priority = 5, dependsOnMethods = "deleteBookFromCollection")
    public void checkIfBookDeleted() {
        test = report.createTest("Verify Book Deletion");

        response = given()
                .header("Authorization", getAuthHeader())
                .when()
                .get(GET_USER + AccountsTest.id)
                .then()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        boolean notPresent = !responseBody.contains(isbnNumber);
        logAndAssertResponse("Verify Book Removed", responseBody, notPresent, "Book no longer appears in user's collection.");
    }
}
