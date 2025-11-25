package polymorphia.maze;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import polymorphia.characters.Character;
import polymorphia.characters.CharacterFactory;

import static org.junit.jupiter.api.Assertions.*;
import static polymorphia.characters.Character.HEALTH_LOST_IN_MOVING_ROOMS;

public class RoomTest {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(RoomTest.class);
    CharacterFactory characterFactory = new CharacterFactory();

    @Test
    void getRandomNeighbor() {
        Room room = new Room("onlyRoom");
        Room neighbor = new Room("neighbor");
        room.addNeighbor(neighbor);

        assertEquals(room.getRandomNeighbor(), neighbor);
    }

    @Test
    void testGetRandomNeighborOnRoomWithNoNeighbors() {
        Room room = new Room("onlyRoom");

        assertNull(room.getRandomNeighbor());
    }

    @Test
    void testToString() {
        Room room = new Room("onlyRoom");
        room.add(characterFactory.createKnight("Bilbo"));
        room.add(characterFactory.createCreature("Ogre"));

        System.out.println(room);

        assertTrue(room.toString().contains("onlyRoom"));
        assertTrue(room.toString().contains("Bilbo"));
        assertTrue(room.toString().contains("Ogre"));
    }

    @Test
    void testMovingRoomLossOfQuarterPoint() {
        Character joe = characterFactory.createKnight("Joe");
        Room currentRoom = new Room("currentRoom");
        Room newRoom = new Room("newRoom");
        currentRoom.addNeighbor(newRoom);
        joe.enterRoom(currentRoom);

        // Since nothing is in the current room with Joe, he should move to the new room
        joe.doAction();

        logger.info("After moving rooms, Joe's health is: " + joe.getHealth());
        assertEquals(CharacterFactory.DEFAULT_FIGHTER_INITIAL_HEALTH - HEALTH_LOST_IN_MOVING_ROOMS, joe.getHealth());
        assertEquals(newRoom, joe.getCurrentLocation());
    }
}
