package polymorphia.characters;

import org.junit.jupiter.api.Test;
import polymorphia.artifacts.ArtifactFactory;
import polymorphia.artifacts.IArtifact;
import polymorphia.maze.Maze;
import polymorphia.maze.RoomFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GluttonTest {
    CharacterFactory characterFactory = new CharacterFactory();
    ArtifactFactory artifactFactory = new ArtifactFactory();
    RoomFactory roomFactory = new RoomFactory();

    @Test
    void testEating() {
        // Arrange
        double initialHealth = 3.0;
        Character glutton = characterFactory.createGlutton("Glutton", initialHealth);
        Character ogre = characterFactory.createCreature("Ogre", initialHealth);
        IArtifact cake = artifactFactory.createFood("Cake");
        Maze maze = Maze.getNewBuilder(roomFactory)
                .createFullyConnectedRooms(List.of("OnlyRoom"))
                .addCharacters(List.of(glutton, ogre))
                .addArtifacts(List.of(cake))
                .build();

        // Act - the glutton should not fight. It should eat
        glutton.doAction();

        // Assert – the glutton ate the cake and gain the health from it
        assertEquals(initialHealth + cake.getHealthValue(), glutton.getHealth());
    }

    @Test
    void testFighting() {
        // Arrange - put Demon in a room with Creature
        Character glutton = characterFactory.createGlutton("Glutton");
        Character orc = characterFactory.createCreature("Orc");
        IArtifact steak = artifactFactory.createFood("Steak");
        Maze twoRoomMaze = Maze.getNewBuilder(roomFactory)
                .createFullyConnectedRooms(List.of("initial", "final"))
                .addToRoom("initial", glutton)
                .addToRoom("initial", orc)
                .addToRoom("initial", steak)
                .build();

        // Act - the glutton will eat
        glutton.doAction();

        // Assert – the coward ran to the other room
        assertEquals(CharacterFactory.DEFAULT_CREATURE_INITIAL_HEALTH, orc.getHealth());
        assertFalse(twoRoomMaze.getRoom("initial").hasFood());
        assertEquals(CharacterFactory.DEFAULT_GLUTTON_INITIAL_HEALTH + steak.getHealthValue(), glutton.getHealth());
    }
}
