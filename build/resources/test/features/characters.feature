Feature: Character behavior
  As a game player
  I want to see how various characters react to different situations
  So that I can see how they interact with each other

  Scenario: A Knight moves if nothing is in the current room
    Given a fully-connected maze with the following rooms:
      | Starting |
      | Final    |
    And a knight named Galahad in the Starting room

    When Galahad plays a turn

    Then Final room contains Galahad

  Scenario: A Knight will fight a Creature even if he can run away
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And a knight named Galahad in the Starting room
    And a creature named Ogre in the Starting room

    When Galahad plays a turn

    Then Ogre lost some health

  Scenario: A Knight will eat food if no creature is in the room
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And the Starting room contains the following food items:
      | Name    | Value |
      | cupcake | 1.0   |
    And a knight named Galahad in the Starting room

    When Galahad plays a turn

    Then the Starting room no longer contains cupcake
    And Galahad is in the Starting room
    And Galahad has gained 1.0 in health


  Scenario: Coward will run away if it can
    Given a maze with the following rooms:
      | Starting  |
      | OtherRoom |
      | Third     |
      | Fourth    |
    And each room is connected to 2 other rooms via 1-way connections
    And a coward named RunAway
    And RunAway is in the Starting room
    And a creature named Ogre
    And Ogre is in the Starting room

    When RunAway plays a turn

    Then Ogre did not lose some health
    And RunAway lost some health
    And RunAway is now not in the Starting room


  Scenario: A Demon will fight a Glutton even when food and neighboring rooms are available
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And a glutton named Eats-a-lot in the Starting room
    And a demon named Satan in the Starting room
    And the Starting room contains the following food items:
      | Name  | Value |
      | apple | 1.0   |

    When Satan plays a turn

    Then Eats-a-lot lost some health
    And Satan lost some health