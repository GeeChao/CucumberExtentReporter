@accepted
Feature: My First Feature

  Scenario: My First Scenario
    Given I have 10 cukes in my belly
    Then I print

  Scenario Outline: My Second Scenario
    Given I have <arg> cukes in my bellies
    Then I print
    Examples:
      |arg  |
    |     6|
      |     7|
      |     8|


