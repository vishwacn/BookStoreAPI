package steps;

import static constants.EndPoints.*;
import static io.restassured.RestAssured.given;
import base.BaseTest;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import tests.AccountsTest;

import org.testng.Assert;
import com.aventstack.extentreports.Status;

public class NegativeSteps extends BaseTest {

    private Response response;
    private String requestBody;
    private String method;
    private String endpoint;
    private boolean authRequired = false;
    private String pathParamKey;
    private String pathParamValue;
    private int expectedStatus;
    private String expectedMessage;

    @Given("the client prepares a {string} request to {string} with body:")
    public void the_client_prepares_request(String method, String endpointKey, String body) {
        this.method = method;
        this.requestBody = body;

        switch (endpointKey) {
            case "CREATE_USER" -> this.endpoint = CREATE_USER;
            case "GENERATE_TOKEN" -> this.endpoint = GENERATE_TOKEN;
            case "ADD_BOOKS" -> this.endpoint = ADD_BOOKS;
            case "GET_USER" -> this.endpoint = GET_USER;
            case "UPDATE_BOOKS" -> this.endpoint = UPDATE_BOOKS;
            case "DELETE_BOOK" -> this.endpoint = DELETE_BOOK;
            default -> throw new IllegalArgumentException("Unknown endpoint: " + endpointKey);
        }
    }

    @And("authorization is {string}")
    public void authorization_is(String authRequiredFlag) {
        this.authRequired = authRequiredFlag.equalsIgnoreCase("required");
    }

    @And("path parameter {string} is set to {string}")
    public void path_parameter_is(String key, String value) {
        this.pathParamKey = key;
        this.pathParamValue = value;
    }

    @When("the request is sent")
    public void the_request_is_sent() {
        var req = given().contentType(ContentType.JSON).body(requestBody);

        if (authRequired) {
            req.header("Authorization", "Bearer " + AccountsTest.token);
        }

        if (pathParamKey != null) {
            req.pathParam(pathParamKey, pathParamValue);
        }

        this.response = req.when().request(method, endpoint + (pathParamKey != null ? "{" + pathParamKey + "}" : ""))
                .then().extract().response();
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatus) {
        this.expectedStatus = expectedStatus;
        Assert.assertEquals(response.getStatusCode(), expectedStatus.intValue());
    }

    @And("the response should contain {string}")
    public void the_response_should_contain(String expectedMsg) {
        this.expectedMessage = expectedMsg;
        String responseBody = response.asString();
        Assert.assertTrue(responseBody.contains(expectedMsg), "Expected message not found.");
        test.log(Status.INFO, "Response: " + responseBody);
        test.log(Status.PASS, "Validation Passed with Status: " + expectedStatus);
    }
}
