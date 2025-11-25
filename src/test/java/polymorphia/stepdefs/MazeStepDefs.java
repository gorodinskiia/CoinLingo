package polymorphia.stepdefs;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import polymorphia.FixedDie;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.artifacts.ArtifactType;
import polymorphia.artifacts.IArtifact;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;
import polymorphia.maze.Room;


public class MazeStepDefs {
    World world;
    private static final int DEFAULT_NUMBER_OF_ROOMS = 5;
    private static final int DEFAULT_MIN_CONNECTIONS = 2;
    private static final String BIDIRECTIONAL = "2-way";
    private static final CharacterFactory characterFactory = new CharacterFactory();
    private static final ArtifactFactory artifactFactory = new ArtifactFactory();
    CharacterStepDefs characterStepDefs;

    public MazeStepDefs(World world, CharacterStepDefs characterStepDefs) {
        this.world = world;
        this.characterStepDefs = characterStepDefs;
    }

    @Given("a maze with the following attributes:")
    public void aMazeWithTheFollowingAttributes(Map<String, Integer> mazeArgs) {
        int numRooms = DEFAULT_NUMBER_OF_ROOMS;
        int numConnections = DEFAULT_MIN_CONNECTIONS;
        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            switch (key) {
                case "number of rooms":
                    numRooms = value;
                    break;
                case "number of connections":
                    numConnections = value;
                    break;
            }
        }

        world.mazeBuilder.createConnectedRooms(numConnections, numRooms);
        addToRooms(mazeArgs);
    }

    private void addToRooms(Map<String, Integer> mazeArgs) {
        for (Map.Entry<String, Integer> entry : mazeArgs.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            switch (key) {
                case "number of food items":
                    world.mazeBuilder.addArtifacts(artifactFactory.createFoodItems(value));
                    break;
                case "number of knights":
                    world.mazeBuilder.addCharacters(characterFactory.createKnights(value));
                    break;
                case "number of gluttons":
                    world.mazeBuilder.addCharacters(characterFactory.createGluttons(value));
                    break;
                case "number of cowards":
                    world.mazeBuilder.addCharacters(characterFactory.createCowards(value));
                    break;
                case "number of creatures":
                    world.mazeBuilder.addCharacters(characterFactory.createCreatures(value));
                    break;
                case "number of demons":
                    world.mazeBuilder.addCharacters(characterFactory.createDemons(value));
                    break;
                case "number of armored suits":
                    world.mazeBuilder.addArtifacts(artifactFactory.createArmoredSuits(value));
                    break;
            }
        }
    }

    @And("^a (knight|glutton|coward|creature|demon) named (.+) in the (\\w+) room$")
    public Character createCharacterAndPutInRoom(String characterType, String characterName, String roomName) {
        Character character = characterStepDefs.createCharacter(characterType, characterName);
        world.mazeBuilder.addToRoom(roomName, character);
        return character;
    }

    @And("^a (knight|glutton|coward|creature|demon) named (.+) in the (\\w+) room with a fixed die of (\\d)$")
    public void createCharacterAndPutInRoom(String characterType, String characterName, String roomName, int fixedRollValue) {
        Character character = createCharacterAndPutInRoom(characterType, characterName, roomName);
        character.setDie(new FixedDie(fixedRollValue));
    }

    @When("{word} plays a turn")
    public void characterPlaysATurn(String characterName) {
        world.createGame();
        Character registryCharacter = world.getCharacter(characterName);
        Room currentRoom = registryCharacter.getCurrentLocation();

        Character liveCharacter = currentRoom.getLivingCharacters().stream()
                .filter(c -> c.getName().contains(characterName))
                .findFirst()
                .orElse(registryCharacter);

        liveCharacter.doAction();
    }

    @Then("{word} room contains {word}")
    public void roomContainsCharacter(String roomName, String characterName) {
        Room room = world.getMaze().getRoom(roomName);
        Character character = world.getCharacter(characterName);
        assertTrue(room.contains(character));
    }

    @Given("a fully-connected maze with the following rooms:")
    public void aFullyConnectedMazeWithTheFollowingRooms(List<String> roomNames) {
        world.mazeBuilder.createFullyConnectedRooms(roomNames);
    }

    @And("the {word} room contains the following food items:")
    public void roomContainsTheFollowingFoodItems(String roomName, List<Map<String, String>> foodDescriptions) {
        Room room = world.getMaze().getRoom(roomName);
        for (Map<String, String> foodDescription : foodDescriptions) {
            IArtifact food = artifactFactory.createFood(
                    foodDescription.get("Name"),
                    Double.parseDouble(foodDescription.get("Value")));
            room.add(food);
            world.putArtifact(food.getName(), food);
        }
    }

    @And("the {word} room contains the following artifacts:")
    public void roomContainsTheFollowingArtifacts(String roomName, List<Map<String, String>> artifactDescriptions) {
        Room room = world.getMaze().getRoom(roomName);
        for (Map<String, String> artifactDescription : artifactDescriptions) {
            IArtifact artifact = artifactFactory.create(
                    ArtifactType.valueOf(artifactDescription.get("Type")),
                    artifactDescription.get("Name"),
                    Double.parseDouble(artifactDescription.get("Health Value")),
                    Double.parseDouble(artifactDescription.get("Defense Value")),
                    Double.parseDouble(artifactDescription.get("Moving Cost")));
            room.add(artifact);
            world.artifacts.put(artifact.getName(), artifact);
        }
    }


    @Then("^the (.+) room no longer contains (.+)$")
    public void roomNoLongerContainsFoodItem(String roomName, String foodName) {
        Room room = world.getMaze().getRoom(roomName);
        assertFalse(room.contains(world.getArtifact(foodName)));
    }

    @And("^(.+) is now(| not) in the (.+) room$")
    public void characterIsInRoom(String characterName, String not, String roomName) {
        Character character = world.getCharacter(characterName);
        if (not.equals(" not")) {
            assertNotEquals(roomName, character.getCurrentLocation().getName());
        } else {
            assertEquals(roomName, character.getCurrentLocation().getName());
        }
    }

    @And("{word} has gained {double} in health")
    public void characterHasGainedInHealth(String characterName, Double lostHealth) {
        Character character = world.getCharacter(characterName);
        assertEquals(world.getInitialHealth(characterName) + lostHealth, character.getHealth());
    }

    @And("the {word} room has {int} food items")
    public void startingRoomHasNoFoodItems(String roomName, int expectedFoodItems) {
        Room room = world.getMaze().getRoom(roomName);
        assertEquals(expectedFoodItems, room.getFoodItems().size());
    }

    @Given("^a (unidirectional|bidirectional), (randomly|sequentially)-distributed maze with the following attributes:$")
    public void connectedDistributedMazeWithTheFollowingAttributes(String roomConnectionType, String distributionType, Map<String, Integer> mazeArgs) {
        world.mazeBuilder.setConnectionDirection(Objects.equals(roomConnectionType, "bidirectional"));
        if (Objects.equals(distributionType, "randomly")) {
            world.mazeBuilder.distributeRandomly();
        } else {
            world.mazeBuilder.distributeSequentially();
        }
        aMazeWithTheFollowingAttributes(mazeArgs);
    }

    @Given("a maze with the following rooms:")
    public void aMazeWithTheFollowingRooms(List<String> roomNames) {
        world.mazeBuilder.createRooms(roomNames);
    }

    @And("each room is connected to {int} other rooms via {word} connections")
    public void eachRoomIsConnectedToOtherRoomsViaConnections(int numberOfConnections, String connectionType) {
        world.mazeBuilder.setConnectionDirection(Objects.equals(connectionType, BIDIRECTIONAL));
        world.mazeBuilder.connectRooms(numberOfConnections);
    }

    @And("{word} is in the {word} room")
    public void characterIsInTheRoom(String characterName, String roomName) {
        world.mazeBuilder.addToRoom(roomName, world.getCharacter(characterName));
    }

    @And("there is no armor in {word} room")
    public void thereIsNoArmorInStartingRoom(String roomName) {
        Room room = world.mazeBuilder.getRoom(roomName);
        Optional<IArtifact> armor = room.getArtifact(ArtifactType.Armor);
        assertFalse(armor.isPresent());
    }

    @Given("there is a {string} student loan of {double} in the {string}")
    public void placeStudentLoanInRoom(String name, double debtAmount, String roomName) {

        Room room = world.getMaze().getRoom(roomName);
        if (room == null) {
            throw new AssertionError("Room not found: " + roomName);
        }

        IArtifact loan = artifactFactory.createStudentLoan(name, debtAmount);

        room.add(loan);

        world.artifacts.put(loan.getName(), loan);
    }

    @Then("there is no StudentLoan in the {word} room")
    public void noStudentLoanInRoom(String roomName) {
        Room room = world.getMaze().getRoom(roomName);
        assertFalse(room.getStudentLoan().isPresent(), "Expected no student loan in room: " + roomName);
    }
}
