Feature: Negative Tests for BookStore API

Scenario: Create User with Weak Password
Given the client prepares a "POST" request to "CREATE_USER" with body:
      """
      { "userName": "weakUser123", "password": "12345" }
      """
And authorization is "not required"
When the request is sent
Then the response status should be 400
And the response should contain "Passwords must have"

Scenario: Generate Token with Invalid Credentials
Given the client prepares a "POST" request to "GENERATE_TOKEN" with body:
      """
      { "userName": "nonexistentUser", "password": "wrongPass1!" }
      """
And authorization is "not required"
When the request is sent
Then the response status should be 200
And the response should contain "Failed"

Scenario: Create Book without Authorization
Given the client prepares a "POST" request to "ADD_BOOKS" with body:
      """
      { "userId": "<user_id>", "collectionOfIsbns": [{ "isbn": "<isbn>" }] }
      """
And authorization is "not required"
When the request is sent
Then the response status should be 401
And the response should contain "User not authorized!"
