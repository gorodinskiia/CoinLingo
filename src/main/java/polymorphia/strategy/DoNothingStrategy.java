package polymorphia.strategy;

import polymorphia.characters.Character;
import polymorphia.maze.Room;

public class DoNothingStrategy extends PlayStrategy {

    @Override
    public void doAction(Character character, Room currentRoom) {
        // Do nothing
    }
}
