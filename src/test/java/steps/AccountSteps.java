package steps;
import static constants.EndPoints.*;
import base.BaseTest;
import config.ConfigManager;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import org.testng.Assert;

public class AccountSteps extends BaseTest {

    private String username;
    private String password;
    private String requestBody;
    private Response response;

    public static String token;
    public static String userId;

    @Given("a new username and password")
    public void a_new_username_and_password() {
        username = "user" + (int) (Math.random() * 10000);
        password = ConfigManager.get("password");
        requestBody = String.format("""
            {
              "userName": "%s",
              "password": "%s"
            }
            """, username, password);
    }

    @When("the user sends a create account request")
    public void send_create_account_request() {
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(CREATE_USER)
                .then()
                .extract().response();

        if (response.statusCode() == 201)
            userId = response.jsonPath().getString("userID");

        test = report.createTest("Create Account");
        test.info("Create User Response: " + response.getBody().asString());
    }

    @Then("the account should be created successfully")
    public void verify_account_creation() {
        Assert.assertEquals(response.statusCode(), 201, "Expected 201 Created");
        Assert.assertNotNull(userId, "UserID should not be null");
        test.pass("User created with ID: " + userId);
    }

    @Given("a valid username and password")
    public void valid_user_credentials() {
        requestBody = String.format("""
            {
              "userName": "%s",
              "password": "%s"
            }
            """, username, password);
    }

    @When("the user requests a token")
    public void user_requests_token() {
        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(GENERATE_TOKEN)
                .then()
                .extract().response();

        if (response.statusCode() == 200)
            token = response.jsonPath().getString("token");

        test = report.createTest("Generate Token");
        test.info("Token Response: " + response.getBody().asString());
    }

    @Then("a valid token should be returned")
    public void validate_token_response() {
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");
        Assert.assertNotNull(token, "Token should not be null");
        test.pass("Token generated: " + token);
    }

    @Given("the user has a valid token")
    public void user_has_valid_token() {
        Assert.assertNotNull(token, "Token must be generated before accessing user info");
    }

    @When("the user fetches their account details")
    public void fetch_user_details() {
        response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .pathParam("userId", userId)
                .when()
                .get(GET_USER + "{userId}")
                .then()
                .extract().response();

        test = report.createTest("Fetch User Info");
        test.info("User Info Response: " + response.getBody().asString());
    }

    @Then("the details should be returned successfully")
    public void verify_user_details() {
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");
        test.pass("User details fetched successfully.");
    }
}
