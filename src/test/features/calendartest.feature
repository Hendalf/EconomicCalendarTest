@CalendarTest
Feature: User opens economic calendar then filter it then open first event and check it and send history to log file

  Scenario: Test economic calendar
    Given User tries to open economic calendar with link "https://www.mql5.com/en/economic-calendar"
    When I filter calendar by "Current month" period and "Medium" importance and "CHF - Swiss frank" currency
    Then I open first event with "CHF" currency
    And I check that  "Medium" importance and "Switzerland" country of event appears
    Then I log history of event for 12 months to log file as table