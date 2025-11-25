Feature: Glutton behavior
  As a game player
  I want to see how various characters react to different situations
  So that I can see how they interact with each other

  Scenario: Behavior of a Glutton
    Given a fully-connected maze with the following rooms:
      | Starting |
      | Final    |
    And a glutton named Tiny in the Starting room
    And the Starting room contains the following food items:
      | Name    | Value |
      | cupcake | 1.0   |

    When Tiny plays a turn

    Then the Starting room no longer contains cupcake
    And Tiny has gained 1.0 in health


  Scenario: Glutton will fight a Creature if he cannot run away
    Given a fully-connected maze with the following rooms:
      | OnlyRoom |
    And a glutton named Eats-a-lot in the OnlyRoom room
    And a creature named Ogre in the OnlyRoom room

    When Eats-a-lot plays a turn

    Then Ogre lost some health


  Scenario: Glutton will eat food if it is available over fighting or running
    Given a fully-connected maze with the following rooms:
      | Starting  |
      | OtherRoom |
    And the Starting room contains the following food items:
      | Name  | Value |
      | apple | 1.0   |
    And a glutton named Eats-a-lot in the Starting room
    And a creature named Ogre in the Starting room

    When Eats-a-lot plays a turn

    Then Eats-a-lot is in the Starting room
    And the Starting room has 0 food items
    And Eats-a-lot has gained 1.0 in health
    And Ogre did not lose some health

  Scenario: Glutton will change into a Fighter when it kills a creature
    Given a maze with the following rooms:
      | Starting |
      | Other    |
    And the Starting room contains the following food items:
      | Name  | Value |
      | apple | 1.0   |
    And the Other room contains the following food items:
      | Name  | Value |
      | chips | 1.5   |
    And a glutton named Eats-a-lot in the Starting room
    And a creature named Ogre in the Starting room
    And a creature named Orc in the Other room

    # Eats-a-lot will eat the apple first
    When Eats-a-lot plays a turn

    Then Eats-a-lot is in the Starting room
    And the Starting room has 0 food items
    And Eats-a-lot has gained 1.0 in health

    # Eats-a-lot will fight the Ogre next
    Given Eats-a-lot will roll a 6
    And Ogre will roll a 1
    When Eats-a-lot plays a turn

    Then Ogre has died
    # Eats-a-lot should now behave as a Fighter

    # Eats-a-lot will now move rooms
    Given Eats-a-lot plays a turn
    Then Eats-a-lot is in the Other room

    # Eats-a-lot will now not eat the chips, but fight the Orc instead
    When Eats-a-lot plays a turn

    Then Orc lost some health
    And a fight has occurred
    # confirming that a fight has taken place