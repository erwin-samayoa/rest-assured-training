Feature:
  Implement BDD style in restassured using restfulbooker API

  Scenario: Perform login with specified credencials
    Given I want to perform an API call
    When I perform post operation on "auth" using "admin" and "password123"
    Then I should get a token