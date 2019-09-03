@ShortLink
Feature: ShortLink
  I can create shortlink with  lst.to API
  and log it to log file and then I can remove this shortlink

  Scenario: create and delete shortlink
    Given User post long link to "https://lst.to/api/v1/link"
    Then User get response with short link
    And Put response DTO to log file
    Then Delete short link using "https://lst.to/api/v1/link/mvlck"