Feature: Register Client

  Scenario: Successful client registration
    Given Client with name Test
    And Client with username test
    And Client with password Sm9zZTEyMzRAcmU1JSY=
    And Client with password salt QHJlNSUm
    And Client with email test@mail.com
    When The client calls /register to register itself
    Then The method response with the client that has been created
    And The method response a 201 HTTP Code