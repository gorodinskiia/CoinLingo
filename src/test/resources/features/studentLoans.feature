Feature: Game play with student loans as artifacts in the maze

Scenario: Student loan is picked up when a character enters the room
    Given a maze with the following rooms:
    | Starting |
    And a glutton named Galahad in the Starting room with a fixed die of 6
    And the Starting room contains the following artifacts:
    | Type        | Name   | Health Value | Moving Cost | Defense Value |
    | StudentLoan | LoanA  | 0.0          | 1.0         | 5.0           |

    When the game plays a turn

    Then Galahad picked up the StudentLoan
    And there is no StudentLoan in the Starting room

    When the game plays a turn

    Given a demon named Satan in the Starting room with a fixed die of 4
    When the game plays a turn

    Then a fight has occurred

    





