package polymorphia.maze;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MazeTest {
    private static final Logger logger = LoggerFactory.getLogger(MazeTest.class);
    private final RoomFactory roomFactory = new RoomFactory();
    private final ArtifactFactory artifactFactory = new ArtifactFactory();
    private final CharacterFactory characterFactory = new CharacterFactory();


    @Test
    void testCreationOf2x2Grid() {
        Maze maze = createPopulated2x2Maze();
        assertEquals(4, maze.size());
        logger.info(maze.toString());
    }

    static Maze createPopulated2x2Maze() {
        CharacterFactory characterFactory = new CharacterFactory();

        Character bilbo = characterFactory.createKnight("Bilbo");
        Character fiona = characterFactory.createKnight("Fiona");
        Character ogre = characterFactory.createCreature("Ogre");
        List<Character> characters = List.of(bilbo, fiona, ogre);
        return Maze.getNewBuilder(new RoomFactory())
                .create2x2Grid()
                .addCharacters(characters)
                .build();
    }

    @Test
    void testCreationOf3x3Grid() {
        Maze maze = createPopulated3x3Maze();
        assertEquals(9, maze.size());
        logger.info(maze.toString());
    }

    static Maze createPopulated3x3Maze() {
        CharacterFactory characterFactory = new CharacterFactory();

        Character bilbo = characterFactory.createKnight("Bilbo");
        Character fiona = characterFactory.createKnight("Fiona");
        Character ogre = characterFactory.createCreature("Ogre");
        Character dragon = characterFactory.createCreature("Dragon");
        Character orc = characterFactory.createCreature("Orc");
        Character medusa = characterFactory.createCreature("Medusa");
        Character troll = characterFactory.createCreature("Troll");
        List<Character> characters = List.of(bilbo, fiona, ogre, dragon, orc, medusa, troll);
        return Maze.getNewBuilder(new RoomFactory())
                .create3x3Grid()
                .addCharacters(characters)
                .build();
    }

    @Test
    void testHasLivingAdventurers() {
        Character bilbo = characterFactory.createKnight("Bilbo");
        Character fiona = characterFactory.createKnight("Fiona");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .create2x2Grid()
                .addCharacters(List.of(bilbo, fiona))
                .build();

        assertTrue(maze.hasLivingAdventurers());
        assertEquals(2, maze.getLivingAdventurers().size());
    }

    @Test
    void testHasLivingCreatures() {
        List<Character> creatures = List.of(
                characterFactory.createCreature("Ogre"),
                characterFactory.createCreature("Dragon"));
        Maze maze = Maze.getNewBuilder(roomFactory)
                .create2x2Grid()
                .addCharacters(characterFactory.createKnights(1))   // to pass validation
                .addCharacters(creatures)
                .build();
        assertTrue(maze.hasLivingCreatures());
        assertEquals(2, maze.getLivingCreatures().size());
    }

    @Test
    void testToString() {
        List<Character> characters = List.of(
                characterFactory.createKnight("Bilbo"),
                characterFactory.createCreature("Ogre"));
        Maze maze = Maze.getNewBuilder(roomFactory)
                .create2x2Grid()
                .addCharacters(characters)
                .build();
        String mazeString = maze.toString();
        logger.info(mazeString);

        assertTrue(mazeString.contains("Bilbo"));
        assertTrue(mazeString.contains("Ogre"));
    }

    @Test
    void testSequentialDistribution() {
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createNRoomsWithMConnections(10, 3)
                .distributeSequentially()
                .addCharacters(List.of(characterFactory.createKnight("Bilbo"))) // to pass validation
                .addArtifacts(artifactFactory.createFoodItems(10))
                .build();

        // Since we added four food items into a ten-room maze, with sequential distribution, each room should have some food.
        for (Room room : maze.getRooms()) {
            assertTrue(room.hasFood());
        }
    }

    @Test
    void testRandomDistribution() {
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createNRoomsWithMConnections(100, 3)
                .distributeRandomly()
                .addCharacters(characterFactory.createKnights(100))
                .build();

        // Since we added a hundred food items into a hundred-room maze, with random distribution,
        // it's likely (not assured) that one room will not have any food in it.
        assertTrue(maze.getRooms().stream().anyMatch(room -> !room.hasLivingAdventurers()));
    }

}
