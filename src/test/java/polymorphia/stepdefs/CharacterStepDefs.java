package polymorphia.stepdefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import polymorphia.Die;
import polymorphia.FixedDie;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Room;
import polymorphia.observers.EventType;

public class CharacterStepDefs {

    World world;
    CharacterFactory characterFactory = new CharacterFactory();

    public CharacterStepDefs(World world) {
        this.world = world;
    }

    @Given("an adventurer {string} with health {double}")
    public void anAdventurerWithHealth(String name, double initialHealth) {
        world.characters.put(name, characterFactory.createKnight(name, initialHealth));
    }

    @And("a creature {string} with health {double}")
    public void aCreatureWithHealth(String name, double initialHealth) {
        world.characters.put(name, characterFactory.createCreature(name, initialHealth));
    }

    @When("this fight occurs:")
    public void thisFightOccurs(List<Map<String, String>> fightData) {
        List<Character> fighters = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            Character fighter = world.characters.get(fightData.get(i).get("foe"));
            int rollValue = Integer.parseInt(fightData.get(i).get("roll"));
            fighter.setDie(new FixedDie(rollValue));
            fighters.add(fighter);
        }
        fighters.getFirst().fight(fighters.getLast());
    }

    @Then("{string} has health {double}")
    public void hasHealth(String name, double expectedHealth) {
        Character character = world.characters.get(name);
        System.out.println(character.getName() + " has health " + character.getHealth());
        System.out.println("Expected health: " + expectedHealth);
        assertEquals(expectedHealth, character.getHealth());
    }

    @Given("{word} will roll a {int}")
    public void characterWillRollAValue(String characterName, int rollValue) {
        Die fixedDie = new FixedDie(rollValue);
        Character character = world.characters.get(characterName);
        character.setDie(fixedDie);
    }

    @Then("{word} has died")
    public void characterHasDied(String characterName) {
        Character character = world.characters.get(characterName);
        assertFalse(character.isAlive());

    }

    public Character createCharacter(String characterType, String characterName) {
        Character character = null;
        switch (characterType) {
            case "knight":
                character = characterFactory.createKnight(characterName);
                break;
            case "glutton":
                character = characterFactory.createGlutton(characterName);
                break;
            case "coward":
                character = characterFactory.createCoward(characterName);
                break;
            case "creature":
                character = characterFactory.createCreature(characterName);
                break;
            case "demon":
                character = characterFactory.createDemon(characterName);
                break;
            default:
                System.out.println("Error -- shouldn't get here");
        }
        world.characters.put(characterName, character);
        assert character != null;
        world.initialHealth.put(characterName, character.getHealth());
        return character;
    }


    @And("^a (knight|glutton|coward|creature|demon) named (\\w+)$")
    public void aCharacter(String characterType, String characterName) {
        createCharacter(characterType, characterName);
    }

    @And("{int} deaths have occurred")
    public void deathsHaveOccurred(int numberOfDeathEvents) {
        assert numberOfDeathEvents <= world.observer.numberOfEventsReceived(EventType.Death);
    }

    @Then("{word} picked up the Armor")
    public void characterPickedUpTheArmor(String characterName) {
        Character character = world.characters.get(characterName);
        Room currentRoom = character.getCurrentLocation();
        Character armoredCharacter = currentRoom.getLivingCharacters().stream()
                .filter(c -> c.getName().contains(characterName))
                .findFirst()
                .get();
        assertTrue(armoredCharacter.getName().toLowerCase().contains("armor"));
    }

    @And("{word} did not lose some health")
    public void characterDidNotLoseSomeHealth(String characterName) {
        Character character = world.getCharacter(characterName);
        assertEquals(world.getInitialHealth(characterName), character.getHealth());
    }

    @And("{word} lost some health")
    public void characterLostSomeHealth(String characterName) {
        Character character = world.getCharacter(characterName);
        assertTrue(character.getHealth() < world.getInitialHealth(characterName));
    }

    @And("{word} has lost {int} times the default fight cost in health")
    public void characterHasLostTheDefaultFightCostInHealth(String characterName, int times) {
        Character character = world.getCharacter(characterName);
        Double initialHealth = world.getInitialHealth(characterName);
        double expected = initialHealth - times * Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME;

        System.out.println("DEBUG: " + characterName + " initialHealth=" + initialHealth
                + " expected=" + expected + " actual=" + character.getHealth()
                + " times=" + times + " perFightLoss=" + Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME);

        assertEquals(expected, character.getHealth(),
                () -> "Expected " + characterName + " to have health " + expected
                        + " (initial " + initialHealth + " minus " + times + " * "
                        + Character.HEALTH_LOST_IN_FIGHT_REGARDLESS_OF_OUTCOME + ") but was "
                        + character.getHealth());
    }

    @And("{word} lost the default-moving cost plus {double} in health")
    public void galahadLostTheDefaultMovingCostPlusInHealth(String characterName, double extraHealthLost) {
        Character character = world.getCharacter(characterName);
        Double initialHealth = world.getInitialHealth(characterName);
        double expected = initialHealth - Character.HEALTH_LOST_IN_MOVING_ROOMS - extraHealthLost;
        assertEquals(expected, character.getHealth());
    }

    @Then("{word} picked up the StudentLoan")
    public void characterPickedUpTheStudentLoan(String characterName) {
        Character character = world.characters.get(characterName);
        Room currentRoom = character.getCurrentLocation();

        Character decoratedCharacter = currentRoom.getLivingCharacters().stream()
                .filter(c -> c.getName().contains(characterName))
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError("Expected the StudentLoan to be picked up, but none was found. Room contents: "
                                + currentRoom.getContents())
                );

        String name = decoratedCharacter.getName();

        assertTrue(
                name.toLowerCase().contains("loan") || name.toLowerCase().contains("student"),
                "Expected the character's name to indicate a student loan, but was: " + name
        );
    }

}