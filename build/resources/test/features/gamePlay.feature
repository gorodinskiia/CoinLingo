Feature: Details of game play
  As a game player
  I want to play a game with a variety of characters
  So that I can see how they interact with each other


  @Disabled
  Scenario: Play the simplest game
    Given a maze with the following attributes:
      | number of rooms      | 2 |
      | number of food items | 3 |
      | number of knights    | 2 |
      | number of creatures  | 2 |
    And an audible observer is attached interested in the following events:
      | Death     |
      | Game Over |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died

  Scenario: Play a game with at least two Death events
    Given a maze with the following attributes:
      | number of rooms         | 2 |
      | number of knights       | 2 |
      | number of armored suits | 2 |
      | number of creatures     | 2 |

    When the game is played in the created maze

    Then the game is over
    And 2 deaths have occurred

  Scenario: Play a complicated game
    Given a maze with the following attributes:
      | number of rooms         | 7  |
      | number of food items    | 20 |
      | number of knights       | 4  |
      | number of armored suits | 4  |
      | number of gluttons      | 3  |
      | number of cowards       | 1  |
      | number of creatures     | 5  |
      | number of demons        | 2  |
#    And an audible observer is attached interested in the following events:
#      | Death     |
#      | Game Over |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died


  Scenario: Fighting
    Given an adventurer "Bilbo" with health 5
    And a creature "Goblin" with health 3

    When this fight occurs:
      | foe    | roll |
      | Bilbo  | 4    |
      | Goblin | 2    |

    Then "Bilbo" has health 4.5
    And "Goblin" has health 0.5


  Scenario: Play a game in a unidirectional, randomly-distributed maze
    Given a unidirectional, randomly-distributed maze with the following attributes:
      | number of rooms                | 11 |
      | number of connections per room | 3  |
      | number of food items           | 10 |
      | number of knights              | 3  |
      | number of gluttons             | 2  |
      | number of cowards              | 1  |
      | number of creatures            | 5  |
      | number of demons               | 2  |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died


  Scenario: Play a game in a bidirectional, sequentially-distributed maze
    Given a bidirectional, sequentially-distributed maze with the following attributes:
      | number of rooms                | 11 |
      | number of connections per room | 3  |
      | number of food items           | 10 |
      | number of knights              | 3  |
      | number of gluttons             | 2  |
      | number of cowards              | 1  |
      | number of creatures            | 5  |
      | number of demons               | 2  |

    When the game is played in the created maze

    Then the game is over
    And all the adventurers or all of the creatures have died
