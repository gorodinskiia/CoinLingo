package polymorphia.characters;

import org.junit.jupiter.api.Test;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CowardTest {
    CharacterFactory characterFactory = new CharacterFactory();
    RoomFactory roomFactory = new RoomFactory();

    @Test
    void testRunning() {
        // Arrange - put creature in room with two adventurers
        Double initialHealth = 5.0;
        Character coward = characterFactory.createCoward("Coward");
        Character dragon = characterFactory.createCreature("Dragon");
        Maze twoRoomMaze = Maze.getNewBuilder(roomFactory)
                .createFullyConnectedRooms(List.of("initial", "final"))
                .addToRoom("initial", coward)
                .addToRoom("initial", dragon)
                .build();

        // Act - the weak knight should fight
        coward.doAction();

        // Assert – the coward ran to the other room and lost some health doing it
        // since there was a creature in the room.
        assertTrue(twoRoomMaze.getRoom("final").hasLivingAdventurers());
        assertTrue(coward.getHealth() < initialHealth);
    }

    @Test
    void testFighting() {
        // Arrange - put creature in room with two adventurers
        Character coward = characterFactory.createCoward("Coward");
        Character satan = characterFactory.createDemon("Satan");

        Maze.getNewBuilder(roomFactory)
                .createFullyConnectedRooms(List.of("initial", "final"))
                .addToRoom("initial", coward)
                .addToRoom("initial", satan)
                .build();

        // Act - the demon fights the coward
        satan.doAction();

        // Assert – the coward ran to the other room
        assertNotEquals(CharacterFactory.DEFAULT_DEMON_INITIAL_HEALTH, satan.getHealth());
    }
}
